package com.users.app.service.mapper;


import com.users.app.domain.*;
import com.users.app.service.dto.AssignTaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssignTask} and its DTO {@link AssignTaskDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssignTaskMapper extends EntityMapper<AssignTaskDTO, AssignTask> {



    default AssignTask fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssignTask assignTask = new AssignTask();
        assignTask.setId(id);
        return assignTask;
    }
}
