package com.users.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.users.app.service.dto.AssignTaskDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.users.app.domain.AssignTask}.
 */
public interface AssignTaskService {

    /**
     * Save a assignTask.
     *
     * @param assignTaskDTO the entity to save.
     * @return the persisted entity.
     */
    AssignTaskDTO save(AssignTaskDTO assignTaskDTO);

    /**
     * Get all the assignTasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssignTaskDTO> findAll(Pageable pageable);


    /**
     * Get the "id" assignTask.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssignTaskDTO> findOne(Long id);

    /**
     * Delete the "id" assignTask.
     *
     * @param id the id of the entity.
     */
    void delete(Long id) throws JsonProcessingException;

    AssignTaskDTO addAssignTask(AssignTaskDTO assignTaskDTO) throws JsonProcessingException;
}
