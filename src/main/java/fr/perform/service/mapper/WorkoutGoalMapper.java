package fr.perform.service.mapper;


import fr.perform.domain.*;
import fr.perform.service.dto.WorkoutGoalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkoutGoal} and its DTO {@link WorkoutGoalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkoutGoalMapper extends EntityMapper<WorkoutGoalDTO, WorkoutGoal> {



    default WorkoutGoal fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkoutGoal workoutGoal = new WorkoutGoal();
        workoutGoal.setId(id);
        return workoutGoal;
    }
}
