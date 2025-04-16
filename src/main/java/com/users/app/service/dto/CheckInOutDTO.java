package com.users.app.service.dto;

import lombok.Data;

import java.time.Instant;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.users.app.domain.CheckInOut} entity.
 */
@Data
public class CheckInOutDTO implements Serializable {

    private Long id;

    private Instant checkInTime;

    private BigDecimal checkInLat;

    private BigDecimal checkInLng;

    private BigDecimal checkInAlt;

    private Instant checkOutTime;

    private BigDecimal checkOutLat;

    private BigDecimal checkOutLng;

    private BigDecimal checkOutAlt;

    private Long authorizerId;

    private Long userId;

    private String authorizerName;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;
}
