package com.users.app.service;

import com.users.app.service.dto.TaskHistoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.users.app.domain.TaskHistory}.
 */
public interface TaskHistoryService {

    /**
     * Save a taskHistory.
     *
     * @param taskHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    TaskHistoryDTO save(TaskHistoryDTO taskHistoryDTO);

    /**
     * Get all the taskHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TaskHistoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" taskHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TaskHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" taskHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
