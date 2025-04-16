package com.users.app.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class CheckInOutRequest {

    private BigDecimal checkInOutLat;

    private BigDecimal checkInOutLng;

    private BigDecimal checkInOutAlt;
}
