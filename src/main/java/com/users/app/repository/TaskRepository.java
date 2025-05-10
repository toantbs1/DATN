package com.users.app.repository;

import com.users.app.domain.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Task entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "select t from Task t " +
        "join AssignTask at " +
        "on t.id = at.taskId " +
        "where at.assigneeId = :userId " +
        "order by t.estimateDate desc")
    Page<Task> findAllTaskAssigned(@Param("userId") Long userId, Pageable pageable);

    Page<Task> findAllByUserId(Long userId, Pageable pageable);
}
