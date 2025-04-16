package com.users.app.service;

import com.users.app.service.dto.CheckInOutDTO;

import com.users.app.service.dto.CheckInOutRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.users.app.domain.CheckInOut}.
 */
public interface CheckInOutService {

    /**
     * Save a checkInOut.
     *
     * @param checkInOutDTO the entity to save.
     * @return the persisted entity.
     */
    CheckInOutDTO save(CheckInOutDTO checkInOutDTO);

    /**
     * Get all the checkInOuts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CheckInOutDTO> findAll(Pageable pageable);


    /**
     * Get the "id" checkInOut.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CheckInOutDTO> findOne(Long id);

    /**
     * Delete the "id" checkInOut.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    CheckInOutDTO checkInOut(CheckInOutRequest checkInRequest);

    CheckInOutDTO getCheckInOut();

    boolean isCheckedIn();

    Map<String, Object> getAttendanceStatistic(Instant from, Instant to);

    Map<String, Object> getAllAttendanceStatistic(Instant from, Instant to);
}
