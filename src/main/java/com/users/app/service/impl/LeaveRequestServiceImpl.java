package com.users.app.service.impl;

import com.users.app.domain.CheckInOut;
import com.users.app.repository.CheckInOutRepository;
import com.users.app.security.SecurityUtils;
import com.users.app.service.LeaveRequestService;
import com.users.app.domain.LeaveRequest;
import com.users.app.repository.LeaveRequestRepository;
import com.users.app.service.dto.LeaveApprovedRequest;
import com.users.app.service.dto.LeaveRequestDTO;
import com.users.app.service.dto.LeaveRequestRequest;
import com.users.app.service.dto.UserInfoDetail;
import com.users.app.service.mapper.LeaveRequestMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LeaveRequest}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestServiceImpl.class);

    private final LeaveRequestRepository leaveRequestRepository;

    private final LeaveRequestMapper leaveRequestMapper;
    private final CheckInOutRepository checkInOutRepository;

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
    public LeaveRequestDTO save(LeaveRequestDTO leaveRequestDTO) {
        log.debug("Request to save LeaveRequest : {}", leaveRequestDTO);
        LeaveRequest leaveRequest = leaveRequestMapper.toEntity(leaveRequestDTO);
        leaveRequest = leaveRequestRepository.save(leaveRequest);
        return leaveRequestMapper.toDto(leaveRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveRequestDTO> findAll() {
        log.debug("Request to get all LeaveRequests");
        UserInfoDetail user = SecurityUtils.getInfoCurrentUserLogin();
        return leaveRequestRepository.findByUserIdOrderByCreatedDate(user.getId())
            .stream().map(leaveRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDTO> findAllApproved() {
        UserInfoDetail user = SecurityUtils.getInfoCurrentUserLogin();
        return leaveRequestRepository.findByApprovedUserIdOrderByCreatedDate(user.getId())
            .stream().map(leaveRequestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public LeaveRequestDTO createLeaveRequest(LeaveRequestRequest leaveRequest) {
        LeaveRequest leaveRequest1 = new LeaveRequest();
        leaveRequest1.setLeaveDate(leaveRequest.getLeaveDate());
        leaveRequest1.setReason(leaveRequest.getReason());
        leaveRequest1.setApprovedUserId(leaveRequest.getApproverUserId());
        leaveRequest1.setApprovedName(leaveRequest.getApproverUserName());
        return leaveRequestMapper.toDto(leaveRequestRepository.save(leaveRequest1));
    }

    @Override
    public LeaveRequestDTO approvedLeaveRequest(LeaveApprovedRequest leaveRequest) {
        LeaveRequest leaveRequest1 = leaveRequestRepository.findById(leaveRequest.getId()).get();
        leaveRequest1.setStatus(leaveRequest.getStatus());
        leaveRequest1.setReply(leaveRequest.getReply());
        if (leaveRequest1.getStatus() != null && leaveRequest1.getStatus().equals("APPROVED")) {
            CheckInOut checkInOut = new CheckInOut();
            checkInOut.setCheckInAlt(altLocationCheck);
            checkInOut.setCheckOutAlt(altLocationCheck);
            checkInOut.setCheckInLat(latLocationCheck);
            checkInOut.setCheckOutLat(latLocationCheck);
            checkInOut.setCheckOutLng(lngLocationCheck);
            checkInOut.setCheckInLng(lngLocationCheck);
            ZoneId zone = ZoneOffset.ofHours(7);

            LocalDate date = leaveRequest1.getLeaveDate().atZone(zone).toLocalDate();

            LocalTime time = LocalTime.parse(startTime);
            LocalTime time1 = LocalTime.parse(endTime);

            LocalDateTime dateTime = LocalDateTime.of(date, time);
            LocalDateTime dateTime1 = LocalDateTime.of(date, time1);

            Instant resultInstant = dateTime.atZone(zone).toInstant();
            Instant resultInstant1 = dateTime1.atZone(zone).toInstant();
            checkInOut.setCheckInTime(resultInstant);
            checkInOut.setCheckOutTime(resultInstant1);
            checkInOut.setAuthorizerId(leaveRequest1.getUserId());
            checkInOut.setUserId(leaveRequest1.getUserId());
            checkInOutRepository.save(checkInOut);
        }
        return leaveRequestMapper.toDto(leaveRequestRepository.save(leaveRequest1));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LeaveRequestDTO> findOne(Long id) {
        log.debug("Request to get LeaveRequest : {}", id);
        return leaveRequestRepository.findById(id)
            .map(leaveRequestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeaveRequest : {}", id);
        leaveRequestRepository.deleteById(id);
    }
}
