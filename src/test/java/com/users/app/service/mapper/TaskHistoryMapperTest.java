package com.users.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TaskHistoryMapperTest {

    private TaskHistoryMapper taskHistoryMapper;

    @BeforeEach
    public void setUp() {
        taskHistoryMapper = new TaskHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taskHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskHistoryMapper.fromId(null)).isNull();
    }
}
