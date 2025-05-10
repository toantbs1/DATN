package com.users.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.users.app.web.rest.TestUtil;

public class TaskHistoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskHistoryDTO.class);
        TaskHistoryDTO taskHistoryDTO1 = new TaskHistoryDTO();
        taskHistoryDTO1.setId(1L);
        TaskHistoryDTO taskHistoryDTO2 = new TaskHistoryDTO();
        assertThat(taskHistoryDTO1).isNotEqualTo(taskHistoryDTO2);
        taskHistoryDTO2.setId(taskHistoryDTO1.getId());
        assertThat(taskHistoryDTO1).isEqualTo(taskHistoryDTO2);
        taskHistoryDTO2.setId(2L);
        assertThat(taskHistoryDTO1).isNotEqualTo(taskHistoryDTO2);
        taskHistoryDTO1.setId(null);
        assertThat(taskHistoryDTO1).isNotEqualTo(taskHistoryDTO2);
    }
}
