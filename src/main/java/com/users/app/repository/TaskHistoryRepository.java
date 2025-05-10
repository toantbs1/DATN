package com.users.app.repository;

import com.users.app.domain.TaskHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the TaskHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Long> {
    List<TaskHistory> findByTaskIdIn(List<Long> taskIds);
}
