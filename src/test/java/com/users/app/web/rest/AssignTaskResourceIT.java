package com.users.app.web.rest;

import com.users.app.DatnApp;
import com.users.app.domain.AssignTask;
import com.users.app.repository.AssignTaskRepository;
import com.users.app.service.AssignTaskService;
import com.users.app.service.dto.AssignTaskDTO;
import com.users.app.service.mapper.AssignTaskMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AssignTaskResource} REST controller.
 */
@SpringBootTest(classes = DatnApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AssignTaskResourceIT {

    private static final Long DEFAULT_TASK_ID = 1L;
    private static final Long UPDATED_TASK_ID = 2L;

    private static final Long DEFAULT_ASSIGNEE_ID = 1L;
    private static final Long UPDATED_ASSIGNEE_ID = 2L;

    private static final Instant DEFAULT_ASSIGN_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ASSIGN_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private AssignTaskRepository assignTaskRepository;

    @Autowired
    private AssignTaskMapper assignTaskMapper;

    @Autowired
    private AssignTaskService assignTaskService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssignTaskMockMvc;

    private AssignTask assignTask;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssignTask createEntity(EntityManager em) {
        AssignTask assignTask = new AssignTask();
            assignTask.setTaskId(DEFAULT_TASK_ID);
        assignTask.setAssigneeId(DEFAULT_ASSIGNEE_ID);
        assignTask.setAssignAt(DEFAULT_ASSIGN_AT);
        assignTask.setUserId(DEFAULT_USER_ID);
        assignTask.setCreatedDate(DEFAULT_CREATED_DATE);
        assignTask.setCreatedBy(DEFAULT_CREATED_BY);
        assignTask.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        assignTask.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return assignTask;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssignTask createUpdatedEntity(EntityManager em) {
        AssignTask assignTask = new AssignTask();
        assignTask.setTaskId(UPDATED_TASK_ID);
        assignTask.setAssigneeId(UPDATED_ASSIGNEE_ID);
        assignTask.setAssignAt(UPDATED_ASSIGN_AT);
        assignTask.setUserId(UPDATED_USER_ID);
        assignTask.setCreatedDate(UPDATED_CREATED_DATE);
        assignTask.setCreatedBy(UPDATED_CREATED_BY);
        assignTask.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        assignTask.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return assignTask;
    }

    @BeforeEach
    public void initTest() {
        assignTask = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssignTask() throws Exception {
        int databaseSizeBeforeCreate = assignTaskRepository.findAll().size();
        // Create the AssignTask
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);
        restAssignTaskMockMvc.perform(post("/api/assign-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignTaskDTO)))
            .andExpect(status().isCreated());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeCreate + 1);
        AssignTask testAssignTask = assignTaskList.get(assignTaskList.size() - 1);
        assertThat(testAssignTask.getTaskId()).isEqualTo(DEFAULT_TASK_ID);
        assertThat(testAssignTask.getAssigneeId()).isEqualTo(DEFAULT_ASSIGNEE_ID);
        assertThat(testAssignTask.getAssignAt()).isEqualTo(DEFAULT_ASSIGN_AT);
        assertThat(testAssignTask.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testAssignTask.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAssignTask.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAssignTask.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testAssignTask.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createAssignTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assignTaskRepository.findAll().size();

        // Create the AssignTask with an existing ID
        assignTask.setId(1L);
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignTaskMockMvc.perform(post("/api/assign-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAssignTasks() throws Exception {
        // Initialize the database
        assignTaskRepository.saveAndFlush(assignTask);

        // Get all the assignTaskList
        restAssignTaskMockMvc.perform(get("/api/assign-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.intValue())))
            .andExpect(jsonPath("$.[*].assigneeId").value(hasItem(DEFAULT_ASSIGNEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].assignAt").value(hasItem(DEFAULT_ASSIGN_AT.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    public void getAssignTask() throws Exception {
        // Initialize the database
        assignTaskRepository.saveAndFlush(assignTask);

        // Get the assignTask
        restAssignTaskMockMvc.perform(get("/api/assign-tasks/{id}", assignTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assignTask.getId().intValue()))
            .andExpect(jsonPath("$.taskId").value(DEFAULT_TASK_ID.intValue()))
            .andExpect(jsonPath("$.assigneeId").value(DEFAULT_ASSIGNEE_ID.intValue()))
            .andExpect(jsonPath("$.assignAt").value(DEFAULT_ASSIGN_AT.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingAssignTask() throws Exception {
        // Get the assignTask
        restAssignTaskMockMvc.perform(get("/api/assign-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssignTask() throws Exception {
        // Initialize the database
        assignTaskRepository.saveAndFlush(assignTask);

        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();

        // Update the assignTask
        AssignTask updatedAssignTask = assignTaskRepository.findById(assignTask.getId()).get();
        // Disconnect from session so that the updates on updatedAssignTask are not directly saved in db
        em.detach(updatedAssignTask);

        updatedAssignTask.setTaskId(UPDATED_TASK_ID);
        updatedAssignTask.setAssigneeId(UPDATED_ASSIGNEE_ID);
        updatedAssignTask.setAssignAt(UPDATED_ASSIGN_AT);
        updatedAssignTask.setUserId(UPDATED_USER_ID);
        updatedAssignTask.setCreatedDate(UPDATED_CREATED_DATE);
        updatedAssignTask.setCreatedBy(UPDATED_CREATED_BY);
        updatedAssignTask.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        updatedAssignTask.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(updatedAssignTask);

        restAssignTaskMockMvc.perform(put("/api/assign-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignTaskDTO)))
            .andExpect(status().isOk());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
        AssignTask testAssignTask = assignTaskList.get(assignTaskList.size() - 1);
        assertThat(testAssignTask.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testAssignTask.getAssigneeId()).isEqualTo(UPDATED_ASSIGNEE_ID);
        assertThat(testAssignTask.getAssignAt()).isEqualTo(UPDATED_ASSIGN_AT);
        assertThat(testAssignTask.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testAssignTask.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAssignTask.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAssignTask.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testAssignTask.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingAssignTask() throws Exception {
        int databaseSizeBeforeUpdate = assignTaskRepository.findAll().size();

        // Create the AssignTask
        AssignTaskDTO assignTaskDTO = assignTaskMapper.toDto(assignTask);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignTaskMockMvc.perform(put("/api/assign-tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignTaskDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssignTask in the database
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssignTask() throws Exception {
        // Initialize the database
        assignTaskRepository.saveAndFlush(assignTask);

        int databaseSizeBeforeDelete = assignTaskRepository.findAll().size();

        // Delete the assignTask
        restAssignTaskMockMvc.perform(delete("/api/assign-tasks/{id}", assignTask.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssignTask> assignTaskList = assignTaskRepository.findAll();
        assertThat(assignTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
