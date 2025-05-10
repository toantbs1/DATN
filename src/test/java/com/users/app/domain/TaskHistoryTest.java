package com.users.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.users.app.web.rest.TestUtil;

public class TaskHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskHistory.class);
        TaskHistory taskHistory1 = new TaskHistory();
        taskHistory1.setId(1L);
        TaskHistory taskHistory2 = new TaskHistory();
        taskHistory2.setId(taskHistory1.getId());
        assertThat(taskHistory1).isEqualTo(taskHistory2);
        taskHistory2.setId(2L);
        assertThat(taskHistory1).isNotEqualTo(taskHistory2);
        taskHistory1.setId(null);
        assertThat(taskHistory1).isNotEqualTo(taskHistory2);
    }
}
