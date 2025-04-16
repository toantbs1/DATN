package com.users.app.web.rest;

import com.users.app.service.CheckInOutService;
import com.users.app.service.dto.CheckInOutRequest;
import com.users.app.web.rest.errors.BadRequestAlertException;
import com.users.app.service.dto.CheckInOutDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link com.users.app.domain.CheckInOut}.
 */
@RestController
@RequestMapping("/api")
public class CheckInOutResource {

    private final Logger log = LoggerFactory.getLogger(CheckInOutResource.class);

    private static final String ENTITY_NAME = "checkInOut";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckInOutService checkInOutService;

    public CheckInOutResource(CheckInOutService checkInOutService) {
        this.checkInOutService = checkInOutService;
    }

    /**
     * {@code POST  /check-in-outs} : Create a new checkInOut.
     *
     * @param checkInOutDTO the checkInOutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkInOutDTO, or with status {@code 400 (Bad Request)} if the checkInOut has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/check-in-outs")
    public ResponseEntity<CheckInOutDTO> createCheckInOut(@RequestBody CheckInOutDTO checkInOutDTO) throws URISyntaxException {
        log.debug("REST request to save CheckInOut : {}", checkInOutDTO);
        if (checkInOutDTO.getId() != null) {
            throw new BadRequestAlertException("A new checkInOut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckInOutDTO result = checkInOutService.save(checkInOutDTO);
        return ResponseEntity.created(new URI("/api/check-in-outs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /check-in-outs} : Updates an existing checkInOut.
     *
     * @param checkInOutDTO the checkInOutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkInOutDTO,
     * or with status {@code 400 (Bad Request)} if the checkInOutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkInOutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/check-in-outs")
    public ResponseEntity<CheckInOutDTO> updateCheckInOut(@RequestBody CheckInOutDTO checkInOutDTO) throws URISyntaxException {
        log.debug("REST request to update CheckInOut : {}", checkInOutDTO);
        if (checkInOutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckInOutDTO result = checkInOutService.save(checkInOutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, checkInOutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /check-in-outs} : get all the checkInOuts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkInOuts in body.
     */
    @GetMapping("/check-in-outs")
    public ResponseEntity<List<CheckInOutDTO>> getAllCheckInOuts(Pageable pageable) {
        log.debug("REST request to get a page of CheckInOuts");
        Page<CheckInOutDTO> page = checkInOutService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /check-in-outs/:id} : get the "id" checkInOut.
     *
     * @param id the id of the checkInOutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkInOutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/check-in-outs/{id}")
    public ResponseEntity<CheckInOutDTO> getCheckInOut(@PathVariable Long id) {
        log.debug("REST request to get CheckInOut : {}", id);
        Optional<CheckInOutDTO> checkInOutDTO = checkInOutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkInOutDTO);
    }

    /**
     * {@code DELETE  /check-in-outs/:id} : delete the "id" checkInOut.
     *
     * @param id the id of the checkInOutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/check-in-outs/{id}")
    public ResponseEntity<Void> deleteCheckInOut(@PathVariable Long id) {
        log.debug("REST request to delete CheckInOut : {}", id);
        checkInOutService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/check-in-outs/user")
    public ResponseEntity<CheckInOutDTO> checkInOutUser(@RequestBody CheckInOutRequest checkInOutDTO) {
        log.debug("REST request to checkInOutUser : {}", checkInOutDTO);
        CheckInOutDTO result = checkInOutService.checkInOut(checkInOutDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/check-in-outs/user")
    public ResponseEntity<CheckInOutDTO> getCheckInOutUser() {
        CheckInOutDTO result = checkInOutService.getCheckInOut();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/check-in-outs/user/is-checked-in")
    public boolean getIsCheckedInUser() {
        return checkInOutService.isCheckedIn();
    }

    @GetMapping("/check-in-outs/user/attendance-statistic")
    public ResponseEntity<?> getAttendanceStatistic(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fromDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate
    ) {
        Map<String, Object> statistics = checkInOutService.getAttendanceStatistic(fromDate, endDate);
        return ResponseEntity.ok().body(statistics);
    }

    @GetMapping("/check-in-outs/attendance-statistics")
    public ResponseEntity<?> getAllAttendanceStatistic(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant fromDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant toDate
    ) {
        Map<String, Object> statistics = checkInOutService.getAllAttendanceStatistic(fromDate, toDate);
        return ResponseEntity.ok().body(statistics);
    }
}
