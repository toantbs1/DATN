package com.users.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CheckInOutMapperTest {

    private CheckInOutMapper checkInOutMapper;

    @BeforeEach
    public void setUp() {
        checkInOutMapper = new CheckInOutMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(checkInOutMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(checkInOutMapper.fromId(null)).isNull();
    }
}
