package com.users.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AssignTaskMapperTest {

    private AssignTaskMapper assignTaskMapper;

    @BeforeEach
    public void setUp() {
        assignTaskMapper = new AssignTaskMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(assignTaskMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(assignTaskMapper.fromId(null)).isNull();
    }
}
