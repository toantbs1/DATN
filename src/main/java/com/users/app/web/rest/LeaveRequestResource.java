package com.users.app.web.rest;

import com.users.app.service.LeaveRequestService;
import com.users.app.service.dto.LeaveApprovedRequest;
import com.users.app.service.dto.LeaveRequestRequest;
import com.users.app.web.rest.errors.BadRequestAlertException;
import com.users.app.service.dto.LeaveRequestDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.users.app.domain.LeaveRequest}.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LeaveRequestResource {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestResource.class);

    private static final String ENTITY_NAME = "leaveRequest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveRequestService leaveRequestService;

    /**
     * {@code POST  /leave-requests} : Create a new leaveRequest.
     *
     * @param leaveRequestDTO the leaveRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveRequestDTO, or with status {@code 400 (Bad Request)} if the leaveRequest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-requests")
    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(@RequestBody LeaveRequestRequest leaveRequestDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveRequest : {}", leaveRequestDTO);
        LeaveRequestDTO result = leaveRequestService.createLeaveRequest(leaveRequestDTO);
        return ResponseEntity.created(new URI("/api/leave-requests/" + result.getId()))
            .body(result);
    }

    @PostMapping("/leave-requests/approve")
    public ResponseEntity<LeaveRequestDTO> createLeaveRequestApproved(@RequestBody LeaveApprovedRequest leaveRequestDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveRequest : {}", leaveRequestDTO);
        LeaveRequestDTO result = leaveRequestService.approvedLeaveRequest(leaveRequestDTO);
        return ResponseEntity.created(new URI("/api/leave-requests/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /leave-requests} : Updates an existing leaveRequest.
     *
     * @param leaveRequestDTO the leaveRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequestDTO,
     * or with status {@code 400 (Bad Request)} if the leaveRequestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveRequestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-requests")
    public ResponseEntity<LeaveRequestDTO> updateLeaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveRequest : {}", leaveRequestDTO);
        if (leaveRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveRequestDTO result = leaveRequestService.save(leaveRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /leave-requests} : get all the leaveRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveRequests in body.
     */
    @GetMapping("/leave-requests/my")
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequests() {
        log.debug("REST request to get a page of LeaveRequests");
        List<LeaveRequestDTO> page = leaveRequestService.findAll();
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/leave-requests/approvals")
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequestsApproved() {
        log.debug("REST request to get a page of LeaveRequests");
        List<LeaveRequestDTO> page = leaveRequestService.findAllApproved();
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /leave-requests/:id} : get the "id" leaveRequest.
     *
     * @param id the id of the leaveRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-requests/{id}")
    public ResponseEntity<LeaveRequestDTO> getLeaveRequest(@PathVariable Long id) {
        log.debug("REST request to get LeaveRequest : {}", id);
        Optional<LeaveRequestDTO> leaveRequestDTO = leaveRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveRequestDTO);
    }

    /**
     * {@code DELETE  /leave-requests/:id} : delete the "id" leaveRequest.
     *
     * @param id the id of the leaveRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-requests/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {
        log.debug("REST request to delete LeaveRequest : {}", id);
        leaveRequestService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
