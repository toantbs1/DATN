package com.users.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.users.app.web.rest.TestUtil;

public class CheckInOutTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckInOut.class);
        CheckInOut checkInOut1 = new CheckInOut();
        checkInOut1.setId(1L);
        CheckInOut checkInOut2 = new CheckInOut();
        checkInOut2.setId(checkInOut1.getId());
        assertThat(checkInOut1).isEqualTo(checkInOut2);
        checkInOut2.setId(2L);
        assertThat(checkInOut1).isNotEqualTo(checkInOut2);
        checkInOut1.setId(null);
        assertThat(checkInOut1).isNotEqualTo(checkInOut2);
    }
}
