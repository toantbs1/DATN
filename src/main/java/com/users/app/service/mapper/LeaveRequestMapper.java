package com.users.app.service.mapper;


import com.users.app.domain.*;
import com.users.app.service.dto.LeaveRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeaveRequest} and its DTO {@link LeaveRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LeaveRequestMapper extends EntityMapper<LeaveRequestDTO, LeaveRequest> {



    default LeaveRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setId(id);
        return leaveRequest;
    }
}
