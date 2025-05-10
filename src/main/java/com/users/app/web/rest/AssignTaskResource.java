package com.users.app.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.users.app.service.AssignTaskService;
import com.users.app.web.rest.errors.BadRequestAlertException;
import com.users.app.service.dto.AssignTaskDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing {@link com.users.app.domain.AssignTask}.
 */
@RestController
@RequestMapping("/api")
public class AssignTaskResource {

    private final Logger log = LoggerFactory.getLogger(AssignTaskResource.class);

    private static final String ENTITY_NAME = "assignTask";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssignTaskService assignTaskService;

    public AssignTaskResource(AssignTaskService assignTaskService) {
        this.assignTaskService = assignTaskService;
    }

    /**
     * {@code POST  /assign-tasks} : Create a new assignTask.
     *
     * @param assignTaskDTO the assignTaskDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assignTaskDTO, or with status {@code 400 (Bad Request)} if the assignTask has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/assign-tasks")
    public ResponseEntity<AssignTaskDTO> createAssignTask(@RequestBody AssignTaskDTO assignTaskDTO) throws URISyntaxException, JsonProcessingException {
        log.debug("REST request to save AssignTask : {}", assignTaskDTO);
        if (assignTaskDTO.getId() != null) {
            throw new BadRequestAlertException("A new assignTask cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssignTaskDTO result = assignTaskService.addAssignTask(assignTaskDTO);
        return ResponseEntity.created(new URI("/api/assign-tasks/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /assign-tasks} : Updates an existing assignTask.
     *
     * @param assignTaskDTO the assignTaskDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assignTaskDTO,
     * or with status {@code 400 (Bad Request)} if the assignTaskDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assignTaskDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/assign-tasks")
    public ResponseEntity<AssignTaskDTO> updateAssignTask(@RequestBody AssignTaskDTO assignTaskDTO) throws URISyntaxException {
        log.debug("REST request to update AssignTask : {}", assignTaskDTO);
        if (assignTaskDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssignTaskDTO result = assignTaskService.save(assignTaskDTO);
        return ResponseEntity.ok()
            .body(result);
    }

    /**
     * {@code GET  /assign-tasks} : get all the assignTasks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignTasks in body.
     */
    @GetMapping("/assign-tasks")
    public ResponseEntity<List<AssignTaskDTO>> getAllAssignTasks(Pageable pageable) {
        log.debug("REST request to get a page of AssignTasks");
        Page<AssignTaskDTO> page = assignTaskService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assign-tasks/:id} : get the "id" assignTask.
     *
     * @param id the id of the assignTaskDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assignTaskDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/assign-tasks/{id}")
    public ResponseEntity<AssignTaskDTO> getAssignTask(@PathVariable Long id) {
        log.debug("REST request to get AssignTask : {}", id);
        Optional<AssignTaskDTO> assignTaskDTO = assignTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assignTaskDTO);
    }

    /**
     * {@code DELETE  /assign-tasks/:id} : delete the "id" assignTask.
     *
     * @param id the id of the assignTaskDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/assign-tasks/{id}")
    public ResponseEntity<Void> deleteAssignTask(@PathVariable Long id) throws JsonProcessingException {
        log.debug("REST request to delete AssignTask : {}", id);
        assignTaskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
