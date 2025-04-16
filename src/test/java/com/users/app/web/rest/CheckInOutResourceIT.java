package com.users.app.web.rest;

import com.users.app.DatnApp;
import com.users.app.domain.CheckInOut;
import com.users.app.repository.CheckInOutRepository;
import com.users.app.service.CheckInOutService;
import com.users.app.service.dto.CheckInOutDTO;
import com.users.app.service.mapper.CheckInOutMapper;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CheckInOutResource} REST controller.
 */
@SpringBootTest(classes = DatnApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CheckInOutResourceIT {

    private static final Instant DEFAULT_CHECK_IN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECK_IN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_CHECK_IN_LAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHECK_IN_LAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CHECK_IN_LNG = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHECK_IN_LNG = new BigDecimal(2);

    private static final Instant DEFAULT_CHECK_OUT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECK_OUT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_CHECK_OUT_LAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHECK_OUT_LAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CHECK_OUT_LNG = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHECK_OUT_LNG = new BigDecimal(2);

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
    private CheckInOutRepository checkInOutRepository;

    @Autowired
    private CheckInOutMapper checkInOutMapper;

    @Autowired
    private CheckInOutService checkInOutService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckInOutMockMvc;

    private CheckInOut checkInOut;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckInOut createEntity(EntityManager em) {
        CheckInOut checkInOut = new CheckInOut().builder()
            .checkInTime(DEFAULT_CHECK_IN_TIME)
            .checkInLat(DEFAULT_CHECK_IN_LAT)
            .checkInLng(DEFAULT_CHECK_IN_LNG)
            .checkOutTime(DEFAULT_CHECK_OUT_TIME)
            .checkOutLat(DEFAULT_CHECK_OUT_LAT)
            .checkOutLng(DEFAULT_CHECK_OUT_LNG)
            .userId(DEFAULT_USER_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY).build();
        return checkInOut;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckInOut createUpdatedEntity(EntityManager em) {
        CheckInOut checkInOut = new CheckInOut().builder()
            .checkInTime(UPDATED_CHECK_IN_TIME)
            .checkInLat(UPDATED_CHECK_IN_LAT)
            .checkInLng(UPDATED_CHECK_IN_LNG)
            .checkOutTime(UPDATED_CHECK_OUT_TIME)
            .checkOutLat(UPDATED_CHECK_OUT_LAT)
            .checkOutLng(UPDATED_CHECK_OUT_LNG)
            .userId(UPDATED_USER_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY).build();
        return checkInOut;
    }

    @BeforeEach
    public void initTest() {
        checkInOut = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckInOut() throws Exception {
        int databaseSizeBeforeCreate = checkInOutRepository.findAll().size();
        // Create the CheckInOut
        CheckInOutDTO checkInOutDTO = checkInOutMapper.toDto(checkInOut);
        restCheckInOutMockMvc.perform(post("/api/check-in-outs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkInOutDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckInOut in the database
        List<CheckInOut> checkInOutList = checkInOutRepository.findAll();
        assertThat(checkInOutList).hasSize(databaseSizeBeforeCreate + 1);
        CheckInOut testCheckInOut = checkInOutList.get(checkInOutList.size() - 1);
        assertThat(testCheckInOut.getCheckInTime()).isEqualTo(DEFAULT_CHECK_IN_TIME);
        assertThat(testCheckInOut.getCheckInLat()).isEqualTo(DEFAULT_CHECK_IN_LAT);
        assertThat(testCheckInOut.getCheckInLng()).isEqualTo(DEFAULT_CHECK_IN_LNG);
        assertThat(testCheckInOut.getCheckOutTime()).isEqualTo(DEFAULT_CHECK_OUT_TIME);
        assertThat(testCheckInOut.getCheckOutLat()).isEqualTo(DEFAULT_CHECK_OUT_LAT);
        assertThat(testCheckInOut.getCheckOutLng()).isEqualTo(DEFAULT_CHECK_OUT_LNG);
        assertThat(testCheckInOut.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCheckInOut.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCheckInOut.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCheckInOut.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
        assertThat(testCheckInOut.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createCheckInOutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkInOutRepository.findAll().size();

        // Create the CheckInOut with an existing ID
        checkInOut.setId(1L);
        CheckInOutDTO checkInOutDTO = checkInOutMapper.toDto(checkInOut);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckInOutMockMvc.perform(post("/api/check-in-outs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkInOutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckInOut in the database
        List<CheckInOut> checkInOutList = checkInOutRepository.findAll();
        assertThat(checkInOutList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCheckInOuts() throws Exception {
        // Initialize the database
        checkInOutRepository.saveAndFlush(checkInOut);

        // Get all the checkInOutList
        restCheckInOutMockMvc.perform(get("/api/check-in-outs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkInOut.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkInTime").value(hasItem(DEFAULT_CHECK_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].checkInLat").value(hasItem(DEFAULT_CHECK_IN_LAT.intValue())))
            .andExpect(jsonPath("$.[*].checkInLng").value(hasItem(DEFAULT_CHECK_IN_LNG.intValue())))
            .andExpect(jsonPath("$.[*].checkOutTime").value(hasItem(DEFAULT_CHECK_OUT_TIME.toString())))
            .andExpect(jsonPath("$.[*].checkOutLat").value(hasItem(DEFAULT_CHECK_OUT_LAT.intValue())))
            .andExpect(jsonPath("$.[*].checkOutLng").value(hasItem(DEFAULT_CHECK_OUT_LNG.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    public void getCheckInOut() throws Exception {
        // Initialize the database
        checkInOutRepository.saveAndFlush(checkInOut);

        // Get the checkInOut
        restCheckInOutMockMvc.perform(get("/api/check-in-outs/{id}", checkInOut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkInOut.getId().intValue()))
            .andExpect(jsonPath("$.checkInTime").value(DEFAULT_CHECK_IN_TIME.toString()))
            .andExpect(jsonPath("$.checkInLat").value(DEFAULT_CHECK_IN_LAT.intValue()))
            .andExpect(jsonPath("$.checkInLng").value(DEFAULT_CHECK_IN_LNG.intValue()))
            .andExpect(jsonPath("$.checkOutTime").value(DEFAULT_CHECK_OUT_TIME.toString()))
            .andExpect(jsonPath("$.checkOutLat").value(DEFAULT_CHECK_OUT_LAT.intValue()))
            .andExpect(jsonPath("$.checkOutLng").value(DEFAULT_CHECK_OUT_LNG.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingCheckInOut() throws Exception {
        // Get the checkInOut
        restCheckInOutMockMvc.perform(get("/api/check-in-outs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckInOut() throws Exception {
        // Initialize the database
        checkInOutRepository.saveAndFlush(checkInOut);

        int databaseSizeBeforeUpdate = checkInOutRepository.findAll().size();

        // Update the checkInOut
        CheckInOut updatedCheckInOut = checkInOutRepository.findById(checkInOut.getId()).get();
        // Disconnect from session so that the updates on updatedCheckInOut are not directly saved in db
        em.detach(updatedCheckInOut);
        updatedCheckInOut.setCheckInTime(UPDATED_CHECK_IN_TIME);
        updatedCheckInOut.setCheckInLat(UPDATED_CHECK_IN_LAT);
        updatedCheckInOut.setCheckInLng(UPDATED_CHECK_IN_LNG);
        updatedCheckInOut.setCheckOutTime(UPDATED_CHECK_OUT_TIME);
        updatedCheckInOut.setCheckOutLat(UPDATED_CHECK_OUT_LAT);
        updatedCheckInOut.setCheckOutLng(UPDATED_CHECK_OUT_LNG);
        updatedCheckInOut.setUserId(UPDATED_USER_ID);
        updatedCheckInOut.setCreatedDate(UPDATED_CREATED_DATE);
        updatedCheckInOut.setCreatedBy(UPDATED_CREATED_BY);
        updatedCheckInOut.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        updatedCheckInOut.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        CheckInOutDTO checkInOutDTO = checkInOutMapper.toDto(updatedCheckInOut);

        restCheckInOutMockMvc.perform(put("/api/check-in-outs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkInOutDTO)))
            .andExpect(status().isOk());

        // Validate the CheckInOut in the database
        List<CheckInOut> checkInOutList = checkInOutRepository.findAll();
        assertThat(checkInOutList).hasSize(databaseSizeBeforeUpdate);
        CheckInOut testCheckInOut = checkInOutList.get(checkInOutList.size() - 1);
        assertThat(testCheckInOut.getCheckInTime()).isEqualTo(UPDATED_CHECK_IN_TIME);
        assertThat(testCheckInOut.getCheckInLat()).isEqualTo(UPDATED_CHECK_IN_LAT);
        assertThat(testCheckInOut.getCheckInLng()).isEqualTo(UPDATED_CHECK_IN_LNG);
        assertThat(testCheckInOut.getCheckOutTime()).isEqualTo(UPDATED_CHECK_OUT_TIME);
        assertThat(testCheckInOut.getCheckOutLat()).isEqualTo(UPDATED_CHECK_OUT_LAT);
        assertThat(testCheckInOut.getCheckOutLng()).isEqualTo(UPDATED_CHECK_OUT_LNG);
        assertThat(testCheckInOut.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCheckInOut.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCheckInOut.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCheckInOut.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
        assertThat(testCheckInOut.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckInOut() throws Exception {
        int databaseSizeBeforeUpdate = checkInOutRepository.findAll().size();

        // Create the CheckInOut
        CheckInOutDTO checkInOutDTO = checkInOutMapper.toDto(checkInOut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckInOutMockMvc.perform(put("/api/check-in-outs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(checkInOutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckInOut in the database
        List<CheckInOut> checkInOutList = checkInOutRepository.findAll();
        assertThat(checkInOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCheckInOut() throws Exception {
        // Initialize the database
        checkInOutRepository.saveAndFlush(checkInOut);

        int databaseSizeBeforeDelete = checkInOutRepository.findAll().size();

        // Delete the checkInOut
        restCheckInOutMockMvc.perform(delete("/api/check-in-outs/{id}", checkInOut.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckInOut> checkInOutList = checkInOutRepository.findAll();
        assertThat(checkInOutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
