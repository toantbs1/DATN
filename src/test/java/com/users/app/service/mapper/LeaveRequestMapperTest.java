package com.users.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LeaveRequestMapperTest {

    private LeaveRequestMapper leaveRequestMapper;

    @BeforeEach
    public void setUp() {
        leaveRequestMapper = new LeaveRequestMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(leaveRequestMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(leaveRequestMapper.fromId(null)).isNull();
    }
}
