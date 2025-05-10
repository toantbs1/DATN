package com.users.app.service.dto;

import lombok.Data;

import java.time.Instant;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * A DTO for the {@link com.users.app.domain.Task} entity.
 */
@Data
public class TaskDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Instant startTime;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private BigDecimal altitude;

    private String status;

    private Long userId;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    private Instant estimateDate;

    private Instant completedDate;

    private List<AssignTaskDTO> assignTasks;

    private List<TaskHistoryDTO> taskHistory;
}
