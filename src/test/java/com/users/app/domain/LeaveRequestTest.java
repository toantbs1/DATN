package com.users.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.users.app.web.rest.TestUtil;

public class LeaveRequestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeaveRequest.class);
        LeaveRequest leaveRequest1 = new LeaveRequest();
        leaveRequest1.setId(1L);
        LeaveRequest leaveRequest2 = new LeaveRequest();
        leaveRequest2.setId(leaveRequest1.getId());
        assertThat(leaveRequest1).isEqualTo(leaveRequest2);
        leaveRequest2.setId(2L);
        assertThat(leaveRequest1).isNotEqualTo(leaveRequest2);
        leaveRequest1.setId(null);
        assertThat(leaveRequest1).isNotEqualTo(leaveRequest2);
    }
}
