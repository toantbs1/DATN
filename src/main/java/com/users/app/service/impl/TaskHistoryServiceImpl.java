package com.users.app.service.impl;

import com.users.app.service.TaskHistoryService;
import com.users.app.domain.TaskHistory;
import com.users.app.repository.TaskHistoryRepository;
import com.users.app.service.dto.TaskHistoryDTO;
import com.users.app.service.mapper.TaskHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskHistory}.
 */
@Service
@Transactional
public class TaskHistoryServiceImpl implements TaskHistoryService {

    private final Logger log = LoggerFactory.getLogger(TaskHistoryServiceImpl.class);

    private final TaskHistoryRepository taskHistoryRepository;

    private final TaskHistoryMapper taskHistoryMapper;

    public TaskHistoryServiceImpl(TaskHistoryRepository taskHistoryRepository, TaskHistoryMapper taskHistoryMapper) {
        this.taskHistoryRepository = taskHistoryRepository;
        this.taskHistoryMapper = taskHistoryMapper;
    }

    @Override
    public TaskHistoryDTO save(TaskHistoryDTO taskHistoryDTO) {
        log.debug("Request to save TaskHistory : {}", taskHistoryDTO);
        TaskHistory taskHistory = taskHistoryMapper.toEntity(taskHistoryDTO);
        taskHistory = taskHistoryRepository.save(taskHistory);
        return taskHistoryMapper.toDto(taskHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TaskHistories");
        return taskHistoryRepository.findAll(pageable)
            .map(taskHistoryMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TaskHistoryDTO> findOne(Long id) {
        log.debug("Request to get TaskHistory : {}", id);
        return taskHistoryRepository.findById(id)
            .map(taskHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TaskHistory : {}", id);
        taskHistoryRepository.deleteById(id);
    }
}
