package com.users.app.service.impl;

import com.users.app.domain.CheckInOut;
import com.users.app.domain.User;
import com.users.app.repository.CheckInOutRepository;
import com.users.app.repository.UserRepository;
import com.users.app.security.SecurityUtils;
import com.users.app.service.CheckInOutService;
import com.users.app.service.dto.CheckInOutCount;
import com.users.app.service.dto.CheckInOutDTO;
import com.users.app.service.dto.CheckInOutRequest;
import com.users.app.service.dto.UserInfoDetail;
import com.users.app.service.mapper.CheckInOutMapper;
import com.users.app.service.utils.DistanceCalculator;
import com.users.app.web.rest.errors.BadRequestAlertException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CheckInOut}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CheckInOutServiceImpl implements CheckInOutService {

    private final Logger log = LoggerFactory.getLogger(CheckInOutServiceImpl.class);

    private final CheckInOutRepository checkInOutRepository;

    private final CheckInOutMapper checkInOutMapper;
    private final UserRepository userRepository;

    @Value("${location.lat}")
    private BigDecimal latLocationCheck;

    @Value("${location.lng}")
    private BigDecimal lngLocationCheck;

    @Value("${location.alt}")
    private BigDecimal altLocationCheck;

    @Value("${time.start}")
    private String startTime;

    @Value("${time.end}")
    private String endTime;

    @Override
    public CheckInOutDTO save(CheckInOutDTO checkInOutDTO) {
        log.debug("Request to save CheckInOut : {}", checkInOutDTO);
        CheckInOut checkInOut = checkInOutMapper.toEntity(checkInOutDTO);
        checkInOut = checkInOutRepository.save(checkInOut);
        return checkInOutMapper.toDto(checkInOut);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CheckInOutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CheckInOuts");
        return checkInOutRepository.findAll(pageable)
            .map(checkInOutMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CheckInOutDTO> findOne(Long id) {
        log.debug("Request to get CheckInOut : {}", id);
        return checkInOutRepository.findById(id)
            .map(checkInOutMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CheckInOut : {}", id);
        checkInOutRepository.deleteById(id);
    }

    @Override
    public CheckInOutDTO checkInOut(CheckInOutRequest checkInOutRequest) {
        Optional<UserInfoDetail> user = Optional.ofNullable(SecurityUtils.getInfoCurrentUserLogin());
        if (user.isPresent()) {
            Optional<CheckInOut> checkInOut = checkInOutRepository.findByAuthorizerIdAndCreatedDate(user.get().getId(), Instant.now());
            if (checkInOut.isPresent()) {
                if (DistanceCalculator.calculateDistance(latLocationCheck, lngLocationCheck, altLocationCheck,
                    checkInOutRequest.getCheckInOutLat(), checkInOutRequest.getCheckInOutLng(), checkInOutRequest.getCheckInOutAlt()).compareTo(BigDecimal.valueOf(10)) < 0) {
                    CheckInOut checkInOutDTO = checkInOut.get();
                    checkInOutDTO.setCheckOutTime(Instant.now().plus(Duration.ofMinutes(5)));
                    checkInOutDTO.setCheckOutLat(checkInOutRequest.getCheckInOutLat());
                    checkInOutDTO.setCheckOutLng(checkInOutRequest.getCheckInOutLng());
                    checkInOutDTO.setCheckOutAlt(checkInOutRequest.getCheckInOutAlt());
                    checkInOutDTO.setAuthorizerId(user.get().getId());
                    checkInOutRepository.save(checkInOutDTO);
                    return checkInOutMapper.toDto(checkInOutDTO);
                } else {
                    throw new BadRequestAlertException("Check out failed!", "", "");
                }
            } else {
                if (DistanceCalculator.calculateDistance(latLocationCheck, lngLocationCheck, altLocationCheck,
                    checkInOutRequest.getCheckInOutLat(), checkInOutRequest.getCheckInOutLng(), checkInOutRequest.getCheckInOutAlt()).compareTo(BigDecimal.valueOf(10)) < 0
                ) {
                    CheckInOut checkInOutDTO = new CheckInOut();
                    checkInOutDTO.setCheckInTime(Instant.now().minus(Duration.ofMinutes(5)));
                    checkInOutDTO.setCheckInLat(checkInOutRequest.getCheckInOutLat());
                    checkInOutDTO.setCheckInLng(checkInOutRequest.getCheckInOutLng());
                    checkInOutDTO.setCheckInAlt(checkInOutRequest.getCheckInOutAlt());
                    checkInOutDTO.setAuthorizerId(user.get().getId());
                    checkInOutRepository.save(checkInOutDTO);
                    return checkInOutMapper.toDto(checkInOutDTO);
                } else {
                    throw new BadRequestAlertException("Check in failed!", "", "");
                }
            }
        } else {
            throw new BadRequestAlertException("Log in failed!", "", "");
        }
    }

    @Override
    public CheckInOutDTO getCheckInOut() {
        UserInfoDetail user = SecurityUtils.getInfoCurrentUserLogin();
        Optional<CheckInOut> checkInOut = checkInOutRepository.findByAuthorizerIdAndCreatedDate(user.getId(), Instant.now());
        if (checkInOut.isPresent()) {
            return checkInOutMapper.toDto(checkInOut.get());
        } else {
            return new CheckInOutDTO();
        }
    }

    @Override
    public boolean isCheckedIn() {
        UserInfoDetail user = SecurityUtils.getInfoCurrentUserLogin();
        Optional<CheckInOut> checkInOut = checkInOutRepository.findByAuthorizerIdAndCreatedDate(user.getId(), Instant.now());
        return checkInOut.isPresent();
    }

    @Override
    public Map<String, Object> getAttendanceStatistic(Instant from, Instant to) {
        Optional<UserInfoDetail> user = Optional.ofNullable(SecurityUtils.getInfoCurrentUserLogin());
        if (user.isPresent()) {
            Map<String, Object> attendanceStatistic = new HashMap<>();
            List<CheckInOutDTO> result = CheckInOutMapper.INSTANCE.toDto(checkInOutRepository.findByAuthorizerIdAndCreatedDateBetween(user.get().getId(), from, to, Time.valueOf(startTime), Time.valueOf(endTime)));
            result.forEach(
                checkInOutDTO -> {
                    checkInOutDTO.setAuthorizerName(user.get().getName());
                }
            );
            List<CheckInOutDTO> lateStart = result.stream()
                .filter(checkInOut -> checkInOut.getCheckInTime()!=null && checkInOut.getCheckInTime()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalTime()
                    .isAfter(LocalTime.parse(startTime))).distinct().collect(Collectors.toList());
            List<CheckInOutDTO> earlyEnd = result.stream()
                .filter(checkInOut -> checkInOut.getCheckOutTime() != null && checkInOut.getCheckOutTime()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalTime()
                    .isBefore(LocalTime.parse(endTime)))
                .distinct().collect(Collectors.toList());
            attendanceStatistic.put("lateStartDetail", lateStart);
            attendanceStatistic.put("earlyEndDetail", earlyEnd);
            attendanceStatistic.put("lateStart", lateStart != null && !lateStart.isEmpty() ? (long) lateStart.size() : 0);
            attendanceStatistic.put("earlyEnd", earlyEnd != null && !earlyEnd.isEmpty() ? (long) earlyEnd.size() : 0);
            return attendanceStatistic;
        } else {
            throw new BadRequestAlertException("Log in failed!", "", "");
        }
    }

    @Override
    public Map<String, Object> getAllAttendanceStatistic(Instant from, Instant to) {
        Optional<UserInfoDetail> user = Optional.ofNullable(SecurityUtils.getInfoCurrentUserLogin());
        if (user.isPresent() && user.get().getAuthorities().contains("ROLE_ADMIN")) {
            List<User> users = userRepository.findAll();
            Map<Long, User> longUserMap = users.stream().collect(Collectors.toMap(User::getId, user1 -> user1));
            Map<String, Object> attendanceStatistic = new HashMap<>();
            List<CheckInOutDTO> result = CheckInOutMapper.INSTANCE.toDto(checkInOutRepository.findAllByAuthorizerIdAndCreatedDateBetween(new ArrayList<>(longUserMap.keySet()), from, to, Time.valueOf(startTime), Time.valueOf(endTime)));
            result.forEach(
                checkInOutDTO -> {
                    checkInOutDTO.setAuthorizerName(longUserMap.get(checkInOutDTO.getAuthorizerId()).getFirstName() + " " + longUserMap.get(checkInOutDTO.getAuthorizerId()).getLastName());
                }
            );
            List<CheckInOutDTO> lateStart = result.stream()
                .filter(checkInOut -> checkInOut.getCheckInTime()!=null && checkInOut.getCheckInTime()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalTime()
                    .isAfter(LocalTime.parse(startTime))).distinct().collect(Collectors.toList());
            List<CheckInOutDTO> earlyEnd = result.stream()
                .filter(checkInOut -> checkInOut.getCheckOutTime() != null && checkInOut.getCheckOutTime()
                    .atZone(ZoneId.of("UTC"))
                    .toLocalTime()
                    .isBefore(LocalTime.parse(endTime)))
                .distinct().collect(Collectors.toList());
            attendanceStatistic.put("lateStart", lateStart != null && !lateStart.isEmpty() ? (long) lateStart.size() : 0);
            attendanceStatistic.put("earlyEnd", earlyEnd != null && !earlyEnd.isEmpty() ? (long) earlyEnd.size() : 0);
            Map<Long, List<CheckInOutDTO>> userMap = result.stream().collect(Collectors.groupingBy(CheckInOutDTO::getAuthorizerId));
            Map<Long, CheckInOutCount> userCountMap = new HashMap<>();
            userMap.keySet().forEach(
                u -> {
                    CheckInOutCount checkInOutCount = new CheckInOutCount();
                    List<CheckInOutDTO> earlyEnd1 = userMap.get(u).stream().filter(checkInOut -> checkInOut.getCheckOutTime()!=null && checkInOut.getCheckOutTime()
                        .atZone(ZoneId.of("UTC"))
                        .toLocalTime()
                        .isBefore(LocalTime.parse(endTime)))
                            .collect(Collectors.toList());
                    List<CheckInOutDTO> lateStart1 = userMap.get(u).stream()
                        .filter(checkInOut -> checkInOut.getCheckInTime()!=null && checkInOut.getCheckInTime()
                            .atZone(ZoneId.of("UTC"))
                            .toLocalTime()
                            .isAfter(LocalTime.parse(startTime))).distinct().collect(Collectors.toList());
                    checkInOutCount.setEarlyEndCount(earlyEnd1 != null && !earlyEnd1.isEmpty() ? (long) earlyEnd1.size() : 0);
                    checkInOutCount.setLateStartCount(lateStart1 != null && !lateStart1.isEmpty() ? (long) lateStart1.size() : 0);
                    userCountMap.put(u, checkInOutCount);
                }
            );
            Map<LocalDate, List<CheckInOutDTO>> dateMap = result.stream()
                .collect(Collectors.groupingBy(c ->
                    c.getCreatedDate().atZone(ZoneId.of("UTC")).toLocalDate()
                ));
            attendanceStatistic.put("userCountMap", userCountMap);
            attendanceStatistic.put("dateMap", dateMap);
            attendanceStatistic.put("userMap", userMap);
            return attendanceStatistic;
        } else {
            throw new BadRequestAlertException("No access permission!", "", "");
        }
    }
}
