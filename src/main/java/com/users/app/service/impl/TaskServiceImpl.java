package com.users.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.app.domain.AssignTask;
import com.users.app.domain.TaskHistory;
import com.users.app.repository.AssignTaskRepository;
import com.users.app.repository.TaskHistoryRepository;
import com.users.app.security.SecurityUtils;
import com.users.app.service.TaskService;
import com.users.app.domain.Task;
import com.users.app.repository.TaskRepository;
import com.users.app.service.dto.TaskDTO;
import com.users.app.service.dto.UserInfoDetail;
import com.users.app.service.mapper.AssignTaskMapper;
import com.users.app.service.mapper.AssignTaskMapperImpl;
import com.users.app.service.mapper.TaskHistoryMapper;
import com.users.app.service.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Task}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final ObjectMapper objectMapper;
    private final TaskHistoryRepository taskHistoryRepository;
    private final AssignTaskRepository assignTaskRepository;
    private final AssignTaskMapper assignTaskMapper;
    private final TaskHistoryMapper taskHistoryMapper;

    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        log.debug("Request to save Task : {}", taskDTO);
        Task task = taskMapper.toEntity(taskDTO);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tasks");
        Optional<UserInfoDetail> user = Optional.ofNullable(SecurityUtils.getInfoCurrentUserLogin());
        if (user.isPresent()) {
            Page<TaskDTO> page = taskRepository.findAllByUserId(user.get().getId(), pageable)
                .map(taskMapper::toDto);
            return setAttributes(page);
        } else {
            throw new RuntimeException("User not logged in");
        }
    }

    private Page<TaskDTO> setAttributes(Page<TaskDTO> page) {
        List<Long> taskIds = page.map(TaskDTO::getId).toList();
        List<AssignTask> assignTasks = assignTaskRepository.findByTaskIdIn(taskIds);
        List<TaskHistory> taskHistories = taskHistoryRepository.findByTaskIdIn(taskIds);
        Map<Long, List<AssignTask>> assignTaskMap = assignTasks.stream().collect(Collectors.groupingBy(AssignTask::getTaskId));
        Map<Long, List<TaskHistory>> taskHistoryMap = taskHistories.stream().collect(Collectors.groupingBy(TaskHistory::getTaskId));
        page.forEach(
            taskDTO -> {
                if(assignTaskMap.containsKey(taskDTO.getId()) && !assignTaskMap.get(taskDTO.getId()).isEmpty()) {
                    taskDTO.setAssignTasks(assignTaskMapper.toDto(assignTaskMap.get(taskDTO.getId())));
                }
                if(taskHistoryMap.containsKey(taskDTO.getId()) && !taskHistoryMap.get(taskDTO.getId()).isEmpty()) {
                    taskDTO.setTaskHistory(taskHistoryMapper.toDto(taskHistoryMap.get(taskDTO.getId())));
                }
            }
        );
        return page;
    }

    @Override
    public Page<TaskDTO> findAllAssigned(Pageable pageable) {
        Optional<UserInfoDetail> user = Optional.ofNullable(SecurityUtils.getInfoCurrentUserLogin());
        if (user.isPresent()) {
            Page<TaskDTO> page = taskRepository.findAllTaskAssigned(user.get().getId(), pageable)
                .map(taskMapper::toDto);
            return setAttributes(page);
        } else {
            throw new RuntimeException("User not logged in");
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TaskDTO> findOne(Long id) {
        log.debug("Request to get Task : {}", id);
        return taskRepository.findById(id)
            .map(taskMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Task : {}", id);
        List<AssignTask> assignTasks = assignTaskRepository.findByTaskIdIn(List.of(id));
        List<TaskHistory> taskHistories = taskHistoryRepository.findByTaskIdIn(List.of(id));
        assignTaskRepository.deleteAll(assignTasks);
        taskHistoryRepository.deleteAll(taskHistories);
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) throws JsonProcessingException {
        Task task = new Task();
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setStartTime(taskDTO.getStartTime());
        task.setEstimateDate(taskDTO.getEstimateDate());
        task.setStatus("TODO");
        taskRepository.save(task);
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setTaskId(task.getId());
        taskHistory.setActionCode("CRE_TSK");
        Map<String, String> map = new HashMap<>();
        map.put("authorizer", task.getUserId().toString());
        taskHistory.setParam(objectMapper.writeValueAsString(map));
        taskHistoryRepository.save(taskHistory);
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) throws JsonProcessingException {
        Optional<Task> updateTask = taskRepository.findById(id);
        if (updateTask.isPresent()) {
            Task task = updateTask.get();
            Map<String, String> map = new HashMap<>();
            map.put("authorizer", task.getUserId().toString());
            if (taskDTO.getName() != null) {
                map.put("oldName", task.getName());
                map.put("newName", taskDTO.getName());
                task.setName(taskDTO.getName());
            }
            if (taskDTO.getDescription() != null) {
                map.put("oldDescription", task.getDescription());
                map.put("newDescription", taskDTO.getDescription());
                task.setDescription(taskDTO.getDescription());
            }
            if (taskDTO.getStartTime() != null) {
                map.put("oldStartTime", task.getStartTime().toString());
                map.put("newStartTime", taskDTO.getStartTime().toString());
                task.setStartTime(taskDTO.getStartTime());
            }
            if (taskDTO.getEstimateDate() != null) {
                map.put("oldEstimateDate", task.getEstimateDate().toString());
                map.put("newEstimateDate", taskDTO.getEstimateDate().toString());
                task.setEstimateDate(taskDTO.getEstimateDate());
            }
            if (taskDTO.getStatus() != null) {
                map.put("oldStatus", task.getStatus());
                map.put("newStatus", taskDTO.getStatus());
                task.setStatus(taskDTO.getStatus());
            }
            if (taskDTO.getCompletedDate() != null) {
                map.put("oldCompletedDate", task.getCompletedDate().toString());
                map.put("newCompletedDate", taskDTO.getCompletedDate().toString());
                task.setCompletedDate(taskDTO.getCompletedDate());
            }
            taskRepository.save(task);
            TaskHistory taskHistory = new TaskHistory();
            taskHistory.setTaskId(task.getId());
            taskHistory.setActionCode("UPD_TSK");
            taskHistory.setParam(objectMapper.writeValueAsString(map));
            taskHistoryRepository.save(taskHistory);
            return taskMapper.toDto(task);
        }
        else {
            throw new RuntimeException("Task not found");
        }
    }
}
