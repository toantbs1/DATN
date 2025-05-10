package com.users.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.users.app.web.rest.TestUtil;

public class AssignTaskDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssignTaskDTO.class);
        AssignTaskDTO assignTaskDTO1 = new AssignTaskDTO();
        assignTaskDTO1.setId(1L);
        AssignTaskDTO assignTaskDTO2 = new AssignTaskDTO();
        assertThat(assignTaskDTO1).isNotEqualTo(assignTaskDTO2);
        assignTaskDTO2.setId(assignTaskDTO1.getId());
        assertThat(assignTaskDTO1).isEqualTo(assignTaskDTO2);
        assignTaskDTO2.setId(2L);
        assertThat(assignTaskDTO1).isNotEqualTo(assignTaskDTO2);
        assignTaskDTO1.setId(null);
        assertThat(assignTaskDTO1).isNotEqualTo(assignTaskDTO2);
    }
}
