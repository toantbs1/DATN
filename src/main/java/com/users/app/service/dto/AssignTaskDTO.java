package com.users.app.service.dto;

import lombok.Data;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.users.app.domain.AssignTask} entity.
 */
@Data
public class AssignTaskDTO implements Serializable {

    private Long id;

    private Long taskId;

    private Long assigneeId;

    private Instant assignAt;

    private Long userId;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;
}
