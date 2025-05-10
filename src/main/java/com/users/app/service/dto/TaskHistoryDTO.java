package com.users.app.service.dto;

import lombok.Data;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.users.app.domain.TaskHistory} entity.
 */
@Data
public class TaskHistoryDTO implements Serializable {

    private Long id;

    private Long taskId;

    private String actionCode;

    private String param;

    private Long userId;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

}
