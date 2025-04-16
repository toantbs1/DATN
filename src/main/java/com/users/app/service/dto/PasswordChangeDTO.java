package com.users.app.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO representing a password change required data - current and new password.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeDTO {
    private String currentPassword;
    private String newPassword;
}
