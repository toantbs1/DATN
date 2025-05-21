package com.users.app.service;

import com.users.app.domain.LeaveRequest;
import com.users.app.service.dto.LeaveApprovedRequest;
import com.users.app.service.dto.LeaveRequestDTO;

import com.users.app.service.dto.LeaveRequestRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.users.app.domain.LeaveRequest}.
 */
public interface LeaveRequestService {

    /**
     * Save a leaveRequest.
     *
     * @param leaveRequestDTO the entity to save.
     * @return the persisted entity.
     */
    LeaveRequestDTO save(LeaveRequestDTO leaveRequestDTO);

    /**
     * Get all the leaveRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    List<LeaveRequestDTO> findAll();

    List<LeaveRequestDTO> findAllApproved();

    LeaveRequestDTO createLeaveRequest(LeaveRequestRequest leaveRequest);

    LeaveRequestDTO approvedLeaveRequest(LeaveApprovedRequest leaveRequest);

    /**
     * Get the "id" leaveRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LeaveRequestDTO> findOne(Long id);

    /**
     * Delete the "id" leaveRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
