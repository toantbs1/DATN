package com.users.app.service.mapper;


import com.users.app.domain.*;
import com.users.app.service.dto.CheckInOutDTO;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link CheckInOut} and its DTO {@link CheckInOutDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckInOutMapper extends EntityMapper<CheckInOutDTO, CheckInOut> {

    CheckInOutMapper INSTANCE = Mappers.getMapper(CheckInOutMapper.class);

    default CheckInOut fromId(Long id) {
        if (id == null) {
            return null;
        }
        CheckInOut checkInOut = new CheckInOut();
        checkInOut.setId(id);
        return checkInOut;
    }
}
