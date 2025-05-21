package com.users.app.web.rest;

import com.users.app.DatnApp;
import com.users.app.domain.LeaveRequest;
import com.users.app.repository.LeaveRequestRepository;
import com.users.app.service.LeaveRequestService;
import com.users.app.service.dto.LeaveRequestDTO;
import com.users.app.service.mapper.LeaveRequestMapper;

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
 * Integration tests for the {@link LeaveRequestResource} REST controller.
 */
@SpringBootTest(classes = DatnApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LeaveRequestResourceIT {

    private static final Instant DEFAULT_LEAVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LEAVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_APPROVED_USER_ID = 1L;
    private static final Long UPDATED_APPROVED_USER_ID = 2L;

    private static final String DEFAULT_APPROVED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APPROVED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REPLY = "AAAAAAAAAA";
    private static final String UPDATED_REPLY = "BBBBBBBBBB";

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
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private LeaveRequestMapper leaveRequestMapper;

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveRequestMockMvc;

    private LeaveRequest leaveRequest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveRequest createEntity(EntityManager em) {
        LeaveRequest leaveRequest = new LeaveRequest().builder()
            .leaveDate(DEFAULT_LEAVE_DATE)
            .reason(DEFAULT_REASON)
            .status(DEFAULT_STATUS)
            .approvedUserId(DEFAULT_APPROVED_USER_ID)
            .approvedName(DEFAULT_APPROVED_NAME)
            .reply(DEFAULT_REPLY)
            .userId(DEFAULT_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY).build();
        return leaveRequest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveRequest createUpdatedEntity(EntityManager em) {
        LeaveRequest leaveRequest = new LeaveRequest().builder()
            .leaveDate(UPDATED_LEAVE_DATE)
            .reason(UPDATED_REASON)
            .status(UPDATED_STATUS)
            .approvedUserId(UPDATED_APPROVED_USER_ID)
            .approvedName(UPDATED_APPROVED_NAME)
            .reply(UPDATED_REPLY)
            .userId(UPDATED_USER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY).build();
        return leaveRequest;
    }

    @BeforeEach
    public void initTest() {
        leaveRequest = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveRequest() throws Exception {
        int databaseSizeBeforeCreate = leaveRequestRepository.findAll().size();
        // Create the LeaveRequest
        LeaveRequestDTO leaveRequestDTO = leaveRequestMapper.toDto(leaveRequest);
        restLeaveRequestMockMvc.perform(post("/api/leave-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveRequestDTO)))
            .andExpect(status().isCreated());

        // Validate the LeaveRequest in the database
        List<LeaveRequest> leaveRequestList = leaveRequestRepository.findAll();
        assertThat(leaveRequestList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveRequest testLeaveRequest = leaveRequestList.get(leaveRequestList.size() - 1);
        assertThat(testLeaveRequest.getLeaveDate()).isEqualTo(DEFAULT_LEAVE_DATE);
        assertThat(testLeaveRequest.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testLeaveRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeaveRequest.getApprovedUserId()).isEqualTo(DEFAULT_APPROVED_USER_ID);
        assertThat(testLeaveRequest.getApprovedName()).isEqualTo(DEFAULT_APPROVED_NAME);
        assertThat(testLeaveRequest.getReply()).isEqualTo(DEFAULT_REPLY);
        assertThat(testLeaveRequest.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testLeaveRequest.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testLeaveRequest.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLeaveRequest.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testLeaveRequest.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createLeaveRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveRequestRepository.findAll().size();

        // Create the LeaveRequest with an existing ID
        leaveRequest.setId(1L);
        LeaveRequestDTO leaveRequestDTO = leaveRequestMapper.toDto(leaveRequest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveRequestMockMvc.perform(post("/api/leave-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequest in the database
        List<LeaveRequest> leaveRequestList = leaveRequestRepository.findAll();
        assertThat(leaveRequestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLeaveRequests() throws Exception {
        // Initialize the database
        leaveRequestRepository.saveAndFlush(leaveRequest);

        // Get all the leaveRequestList
        restLeaveRequestMockMvc.perform(get("/api/leave-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveRequest.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveDate").value(hasItem(DEFAULT_LEAVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].approvedUserId").value(hasItem(DEFAULT_APPROVED_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].approvedName").value(hasItem(DEFAULT_APPROVED_NAME)))
            .andExpect(jsonPath("$.[*].reply").value(hasItem(DEFAULT_REPLY)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    public void getLeaveRequest() throws Exception {
        // Initialize the database
        leaveRequestRepository.saveAndFlush(leaveRequest);

        // Get the leaveRequest
        restLeaveRequestMockMvc.perform(get("/api/leave-requests/{id}", leaveRequest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveRequest.getId().intValue()))
            .andExpect(jsonPath("$.leaveDate").value(DEFAULT_LEAVE_DATE.toString()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.approvedUserId").value(DEFAULT_APPROVED_USER_ID.intValue()))
            .andExpect(jsonPath("$.approvedName").value(DEFAULT_APPROVED_NAME))
            .andExpect(jsonPath("$.reply").value(DEFAULT_REPLY))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingLeaveRequest() throws Exception {
        // Get the leaveRequest
        restLeaveRequestMockMvc.perform(get("/api/leave-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveRequest() throws Exception {
        // Initialize the database
        leaveRequestRepository.saveAndFlush(leaveRequest);

        int databaseSizeBeforeUpdate = leaveRequestRepository.findAll().size();

        // Update the leaveRequest
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.findById(leaveRequest.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveRequest are not directly saved in db
        em.detach(updatedLeaveRequest);
        updatedLeaveRequest.builder()
            .leaveDate(UPDATED_LEAVE_DATE)
            .reason(UPDATED_REASON)
            .status(UPDATED_STATUS)
            .approvedUserId(UPDATED_APPROVED_USER_ID)
            .approvedName(UPDATED_APPROVED_NAME)
            .reply(UPDATED_REPLY)
            .userId(UPDATED_USER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        LeaveRequestDTO leaveRequestDTO = leaveRequestMapper.toDto(updatedLeaveRequest);

        restLeaveRequestMockMvc.perform(put("/api/leave-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveRequestDTO)))
            .andExpect(status().isOk());

        // Validate the LeaveRequest in the database
        List<LeaveRequest> leaveRequestList = leaveRequestRepository.findAll();
        assertThat(leaveRequestList).hasSize(databaseSizeBeforeUpdate);
        LeaveRequest testLeaveRequest = leaveRequestList.get(leaveRequestList.size() - 1);
        assertThat(testLeaveRequest.getLeaveDate()).isEqualTo(UPDATED_LEAVE_DATE);
        assertThat(testLeaveRequest.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testLeaveRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveRequest.getApprovedUserId()).isEqualTo(UPDATED_APPROVED_USER_ID);
        assertThat(testLeaveRequest.getApprovedName()).isEqualTo(UPDATED_APPROVED_NAME);
        assertThat(testLeaveRequest.getReply()).isEqualTo(UPDATED_REPLY);
        assertThat(testLeaveRequest.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testLeaveRequest.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testLeaveRequest.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeaveRequest.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testLeaveRequest.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveRequest() throws Exception {
        int databaseSizeBeforeUpdate = leaveRequestRepository.findAll().size();

        // Create the LeaveRequest
        LeaveRequestDTO leaveRequestDTO = leaveRequestMapper.toDto(leaveRequest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveRequestMockMvc.perform(put("/api/leave-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveRequestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveRequest in the database
        List<LeaveRequest> leaveRequestList = leaveRequestRepository.findAll();
        assertThat(leaveRequestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeaveRequest() throws Exception {
        // Initialize the database
        leaveRequestRepository.saveAndFlush(leaveRequest);

        int databaseSizeBeforeDelete = leaveRequestRepository.findAll().size();

        // Delete the leaveRequest
        restLeaveRequestMockMvc.perform(delete("/api/leave-requests/{id}", leaveRequest.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveRequest> leaveRequestList = leaveRequestRepository.findAll();
        assertThat(leaveRequestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
