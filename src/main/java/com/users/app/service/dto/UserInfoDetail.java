package com.users.app.service.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class UserInfoDetail {
    private Long id;
    private String login;
    private String name;
    private String email;
    private String imageUrl;
    private boolean activated;
    private String langKey;
    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
    private List<String> authorities;
}
