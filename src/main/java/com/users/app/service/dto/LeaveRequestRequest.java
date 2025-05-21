package com.users.app.service.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class LeaveRequestRequest {
    private Instant leaveDate;

    private String reason;

    private Long approverUserId;

    private String approverUserName;
}
