package com.users.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.app.domain.TaskHistory;
import com.users.app.repository.TaskHistoryRepository;
import com.users.app.service.AssignTaskService;
import com.users.app.domain.AssignTask;
import com.users.app.repository.AssignTaskRepository;
import com.users.app.service.dto.AssignTaskDTO;
import com.users.app.service.mapper.AssignTaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AssignTask}.
 */
@Service
@Transactional
public class AssignTaskServiceImpl implements AssignTaskService {

    private final Logger log = LoggerFactory.getLogger(AssignTaskServiceImpl.class);

    private final AssignTaskRepository assignTaskRepository;

    private final AssignTaskMapper assignTaskMapper;
    private final ObjectMapper objectMapper;
    private final TaskHistoryRepository taskHistoryRepository;

    public AssignTaskServiceImpl(AssignTaskRepository assignTaskRepository, AssignTaskMapper assignTaskMapper, @Qualifier("objectMapper") ObjectMapper objectMapper, TaskHistoryRepository taskHistoryRepository) {
        this.assignTaskRepository = assignTaskRepository;
        this.assignTaskMapper = assignTaskMapper;
        this.objectMapper = objectMapper;
        this.taskHistoryRepository = taskHistoryRepository;
    }

    @Override
    public AssignTaskDTO save(AssignTaskDTO assignTaskDTO) {
        log.debug("Request to save AssignTask : {}", assignTaskDTO);
        AssignTask assignTask = assignTaskMapper.toEntity(assignTaskDTO);
        assignTask = assignTaskRepository.save(assignTask);
        return assignTaskMapper.toDto(assignTask);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssignTaskDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssignTasks");
        return assignTaskRepository.findAll(pageable)
            .map(assignTaskMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AssignTaskDTO> findOne(Long id) {
        log.debug("Request to get AssignTask : {}", id);
        return assignTaskRepository.findById(id)
            .map(assignTaskMapper::toDto);
    }

    @Override
    public void delete(Long id) throws JsonProcessingException {
        log.debug("Request to delete AssignTask : {}", id);
        AssignTask assignTask = assignTaskRepository.findById(id).orElse(null);
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTaskId(assignTask.getTaskId());
        taskHistory.setActionCode("DEL_ASSIGN_TASK");
        Map<String, String> map = new HashMap<>();
        map.put("authorizer", assignTask.getUserId().toString());
        map.put("assignee", assignTask.getAssigneeId().toString());
        taskHistory.setParam(objectMapper.writeValueAsString(map));
        taskHistoryRepository.save(taskHistory);
        assignTaskRepository.delete(assignTask);
    }

    @Override
    public AssignTaskDTO addAssignTask(AssignTaskDTO assignTaskDTO) throws JsonProcessingException {
        AssignTask assignTask = new AssignTask();
        assignTask.setTaskId(assignTaskDTO.getTaskId());
        assignTask.setAssigneeId(assignTaskDTO.getAssigneeId());
        assignTask.setAssignAt(Instant.now());
        assignTaskRepository.save(assignTask);
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTaskId(assignTaskDTO.getId());
        taskHistory.setActionCode("ADD_ASSIGN_TASK");
        Map<String, String> map = new HashMap<>();
        map.put("authorizer", assignTask.getUserId().toString());
        map.put("assignee", assignTask.getAssigneeId().toString());
        taskHistory.setParam(objectMapper.writeValueAsString(map));
        taskHistoryRepository.save(taskHistory);
        return assignTaskMapper.toDto(assignTask);
    }
}
