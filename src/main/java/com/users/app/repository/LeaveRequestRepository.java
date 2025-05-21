package com.users.app.repository;

import com.users.app.domain.LeaveRequest;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the LeaveRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByUserIdOrderByCreatedDate(Long userId);

    List<LeaveRequest> findByApprovedUserIdOrderByCreatedDate(Long userId);
}
