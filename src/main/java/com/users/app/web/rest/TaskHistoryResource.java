package com.users.app.web.rest;

import com.users.app.service.TaskHistoryService;
import com.users.app.web.rest.errors.BadRequestAlertException;
import com.users.app.service.dto.TaskHistoryDTO;

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
 * REST controller for managing {@link com.users.app.domain.TaskHistory}.
 */
@RestController
@RequestMapping("/api")
public class TaskHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TaskHistoryResource.class);

    private static final String ENTITY_NAME = "taskHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskHistoryService taskHistoryService;

    public TaskHistoryResource(TaskHistoryService taskHistoryService) {
        this.taskHistoryService = taskHistoryService;
    }

    /**
     * {@code POST  /task-histories} : Create a new taskHistory.
     *
     * @param taskHistoryDTO the taskHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskHistoryDTO, or with status {@code 400 (Bad Request)} if the taskHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-histories")
    public ResponseEntity<TaskHistoryDTO> createTaskHistory(@RequestBody TaskHistoryDTO taskHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save TaskHistory : {}", taskHistoryDTO);
        if (taskHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskHistoryDTO result = taskHistoryService.save(taskHistoryDTO);
        return ResponseEntity.created(new URI("/api/task-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-histories} : Updates an existing taskHistory.
     *
     * @param taskHistoryDTO the taskHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the taskHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-histories")
    public ResponseEntity<TaskHistoryDTO> updateTaskHistory(@RequestBody TaskHistoryDTO taskHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update TaskHistory : {}", taskHistoryDTO);
        if (taskHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskHistoryDTO result = taskHistoryService.save(taskHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, taskHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-histories} : get all the taskHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskHistories in body.
     */
    @GetMapping("/task-histories")
    public ResponseEntity<List<TaskHistoryDTO>> getAllTaskHistories(Pageable pageable) {
        log.debug("REST request to get a page of TaskHistories");
        Page<TaskHistoryDTO> page = taskHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-histories/:id} : get the "id" taskHistory.
     *
     * @param id the id of the taskHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-histories/{id}")
    public ResponseEntity<TaskHistoryDTO> getTaskHistory(@PathVariable Long id) {
        log.debug("REST request to get TaskHistory : {}", id);
        Optional<TaskHistoryDTO> taskHistoryDTO = taskHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskHistoryDTO);
    }

    /**
     * {@code DELETE  /task-histories/:id} : delete the "id" taskHistory.
     *
     * @param id the id of the taskHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-histories/{id}")
    public ResponseEntity<Void> deleteTaskHistory(@PathVariable Long id) {
        log.debug("REST request to delete TaskHistory : {}", id);
        taskHistoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
