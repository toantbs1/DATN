package com.users.app.repository;

import com.users.app.domain.AssignTask;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AssignTask entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssignTaskRepository extends JpaRepository<AssignTask, Long> {
    List<AssignTask> findByTaskIdIn(List<Long> taskIds);
}
