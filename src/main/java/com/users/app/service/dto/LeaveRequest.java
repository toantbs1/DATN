package com.users.app.service.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class LeaveRequest {
    private Instant leaveDate;

    private String reason;

    private Long approverUserId;

    private String approverUserName;
}
