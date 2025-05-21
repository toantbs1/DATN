package com.users.app.service.dto;

import lombok.Data;

@Data
public class LeaveApprovedRequest {
    private Long id;
    private String status;
    private String reply;
}
