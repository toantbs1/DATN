package com.users.app.web.rest;

import com.users.app.DatnApp;
import com.users.app.domain.TaskHistory;
import com.users.app.repository.TaskHistoryRepository;
import com.users.app.service.TaskHistoryService;
import com.users.app.service.dto.TaskHistoryDTO;
import com.users.app.service.mapper.TaskHistoryMapper;

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
 * Integration tests for the {@link TaskHistoryResource} REST controller.
 */
@SpringBootTest(classes = DatnApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaskHistoryResourceIT {

    private static final Long DEFAULT_TASK_ID = 1L;
    private static final Long UPDATED_TASK_ID = 2L;

    private static final String DEFAULT_ACTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PARAM = "AAAAAAAAAA";
    private static final String UPDATED_PARAM = "BBBBBBBBBB";

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
    private TaskHistoryRepository taskHistoryRepository;

    @Autowired
    private TaskHistoryMapper taskHistoryMapper;

    @Autowired
    private TaskHistoryService taskHistoryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaskHistoryMockMvc;

    private TaskHistory taskHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskHistory createEntity(EntityManager em) {
        TaskHistory taskHistory = new TaskHistory().builder()
            .taskId(DEFAULT_TASK_ID)
            .actionCode(DEFAULT_ACTION_CODE)
            .param(DEFAULT_PARAM)
            .userId(DEFAULT_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY).build();
        return taskHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaskHistory createUpdatedEntity(EntityManager em) {
        TaskHistory taskHistory = new TaskHistory().builder()
            .taskId(UPDATED_TASK_ID)
            .actionCode(UPDATED_ACTION_CODE)
            .param(UPDATED_PARAM)
            .userId(UPDATED_USER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY).build();
        return taskHistory;
    }

    @BeforeEach
    public void initTest() {
        taskHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaskHistory() throws Exception {
        int databaseSizeBeforeCreate = taskHistoryRepository.findAll().size();
        // Create the TaskHistory
        TaskHistoryDTO taskHistoryDTO = taskHistoryMapper.toDto(taskHistory);
        restTaskHistoryMockMvc.perform(post("/api/task-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the TaskHistory in the database
        List<TaskHistory> taskHistoryList = taskHistoryRepository.findAll();
        assertThat(taskHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TaskHistory testTaskHistory = taskHistoryList.get(taskHistoryList.size() - 1);
        assertThat(testTaskHistory.getTaskId()).isEqualTo(DEFAULT_TASK_ID);
        assertThat(testTaskHistory.getActionCode()).isEqualTo(DEFAULT_ACTION_CODE);
        assertThat(testTaskHistory.getParam()).isEqualTo(DEFAULT_PARAM);
        assertThat(testTaskHistory.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTaskHistory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTaskHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTaskHistory.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testTaskHistory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createTaskHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taskHistoryRepository.findAll().size();

        // Create the TaskHistory with an existing ID
        taskHistory.setId(1L);
        TaskHistoryDTO taskHistoryDTO = taskHistoryMapper.toDto(taskHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaskHistoryMockMvc.perform(post("/api/task-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskHistory in the database
        List<TaskHistory> taskHistoryList = taskHistoryRepository.findAll();
        assertThat(taskHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaskHistories() throws Exception {
        // Initialize the database
        taskHistoryRepository.saveAndFlush(taskHistory);

        // Get all the taskHistoryList
        restTaskHistoryMockMvc.perform(get("/api/task-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taskHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskId").value(hasItem(DEFAULT_TASK_ID.intValue())))
            .andExpect(jsonPath("$.[*].actionCode").value(hasItem(DEFAULT_ACTION_CODE)))
            .andExpect(jsonPath("$.[*].param").value(hasItem(DEFAULT_PARAM)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    public void getTaskHistory() throws Exception {
        // Initialize the database
        taskHistoryRepository.saveAndFlush(taskHistory);

        // Get the taskHistory
        restTaskHistoryMockMvc.perform(get("/api/task-histories/{id}", taskHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taskHistory.getId().intValue()))
            .andExpect(jsonPath("$.taskId").value(DEFAULT_TASK_ID.intValue()))
            .andExpect(jsonPath("$.actionCode").value(DEFAULT_ACTION_CODE))
            .andExpect(jsonPath("$.param").value(DEFAULT_PARAM))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingTaskHistory() throws Exception {
        // Get the taskHistory
        restTaskHistoryMockMvc.perform(get("/api/task-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaskHistory() throws Exception {
        // Initialize the database
        taskHistoryRepository.saveAndFlush(taskHistory);

        int databaseSizeBeforeUpdate = taskHistoryRepository.findAll().size();

        // Update the taskHistory
        TaskHistory updatedTaskHistory = taskHistoryRepository.findById(taskHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTaskHistory are not directly saved in db
        em.detach(updatedTaskHistory);
        updatedTaskHistory.builder()
            .taskId(UPDATED_TASK_ID)
            .actionCode(UPDATED_ACTION_CODE)
            .param(UPDATED_PARAM)
            .userId(UPDATED_USER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TaskHistoryDTO taskHistoryDTO = taskHistoryMapper.toDto(updatedTaskHistory);

        restTaskHistoryMockMvc.perform(put("/api/task-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the TaskHistory in the database
        List<TaskHistory> taskHistoryList = taskHistoryRepository.findAll();
        assertThat(taskHistoryList).hasSize(databaseSizeBeforeUpdate);
        TaskHistory testTaskHistory = taskHistoryList.get(taskHistoryList.size() - 1);
        assertThat(testTaskHistory.getTaskId()).isEqualTo(UPDATED_TASK_ID);
        assertThat(testTaskHistory.getActionCode()).isEqualTo(UPDATED_ACTION_CODE);
        assertThat(testTaskHistory.getParam()).isEqualTo(UPDATED_PARAM);
        assertThat(testTaskHistory.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTaskHistory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTaskHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTaskHistory.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testTaskHistory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingTaskHistory() throws Exception {
        int databaseSizeBeforeUpdate = taskHistoryRepository.findAll().size();

        // Create the TaskHistory
        TaskHistoryDTO taskHistoryDTO = taskHistoryMapper.toDto(taskHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaskHistoryMockMvc.perform(put("/api/task-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taskHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TaskHistory in the database
        List<TaskHistory> taskHistoryList = taskHistoryRepository.findAll();
        assertThat(taskHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaskHistory() throws Exception {
        // Initialize the database
        taskHistoryRepository.saveAndFlush(taskHistory);

        int databaseSizeBeforeDelete = taskHistoryRepository.findAll().size();

        // Delete the taskHistory
        restTaskHistoryMockMvc.perform(delete("/api/task-histories/{id}", taskHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaskHistory> taskHistoryList = taskHistoryRepository.findAll();
        assertThat(taskHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
