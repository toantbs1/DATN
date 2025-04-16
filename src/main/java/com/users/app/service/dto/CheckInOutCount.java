package com.users.app.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInOutCount {
    private Long earlyEndCount;
    private Long lateStartCount;
}
