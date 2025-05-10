package com.users.app.service.mapper;


import com.users.app.domain.*;
import com.users.app.service.dto.TaskHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskHistory} and its DTO {@link TaskHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TaskHistoryMapper extends EntityMapper<TaskHistoryDTO, TaskHistory> {



    default TaskHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        TaskHistory taskHistory = new TaskHistory();
        taskHistory.setId(id);
        return taskHistory;
    }
}
