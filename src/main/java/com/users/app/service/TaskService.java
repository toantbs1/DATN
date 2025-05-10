package com.users.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.users.app.service.dto.TaskDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.users.app.domain.Task}.
 */
public interface TaskService {

    /**
     * Save a task.
     *
     * @param taskDTO the entity to save.
     * @return the persisted entity.
     */
    TaskDTO save(TaskDTO taskDTO);

    /**
     * Get all the tasks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskDTO> findAll(Pageable pageable);

    Page<TaskDTO> findAllAssigned(Pageable pageable);


    /**
     * Get the "id" task.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskDTO> findOne(Long id);

    /**
     * Delete the "id" task.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    TaskDTO createTask(TaskDTO taskDTO) throws JsonProcessingException;

    TaskDTO updateTask(Long id, TaskDTO taskDTO) throws JsonProcessingException;
}
