package com.users.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.users.app.web.rest.TestUtil;

public class AssignTaskTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssignTask.class);
        AssignTask assignTask1 = new AssignTask();
        assignTask1.setId(1L);
        AssignTask assignTask2 = new AssignTask();
        assignTask2.setId(assignTask1.getId());
        assertThat(assignTask1).isEqualTo(assignTask2);
        assignTask2.setId(2L);
        assertThat(assignTask1).isNotEqualTo(assignTask2);
        assignTask1.setId(null);
        assertThat(assignTask1).isNotEqualTo(assignTask2);
    }
}
