package com.users.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.users.app.web.rest.TestUtil;

public class CheckInOutDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckInOutDTO.class);
        CheckInOutDTO checkInOutDTO1 = new CheckInOutDTO();
        checkInOutDTO1.setId(1L);
        CheckInOutDTO checkInOutDTO2 = new CheckInOutDTO();
        assertThat(checkInOutDTO1).isNotEqualTo(checkInOutDTO2);
        checkInOutDTO2.setId(checkInOutDTO1.getId());
        assertThat(checkInOutDTO1).isEqualTo(checkInOutDTO2);
        checkInOutDTO2.setId(2L);
        assertThat(checkInOutDTO1).isNotEqualTo(checkInOutDTO2);
        checkInOutDTO1.setId(null);
        assertThat(checkInOutDTO1).isNotEqualTo(checkInOutDTO2);
    }
}
