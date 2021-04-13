package fr.perform.service.mapper;


import fr.perform.domain.*;
import fr.perform.service.dto.WorkoutDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Workout} and its DTO {@link WorkoutDTO}.
 */
@Mapper(componentModel = "spring", uses = {WorkoutGoalMapper.class, UserMapper.class})
public interface WorkoutMapper extends EntityMapper<WorkoutDTO, Workout> {

    @Mapping(source = "workoutGoal.id", target = "workoutGoalId")
    @Mapping(source = "user.id", target = "userId")
    WorkoutDTO toDto(Workout workout);

    @Mapping(source = "workoutGoalId", target = "workoutGoal")
    @Mapping(source = "userId", target = "user")
    Workout toEntity(WorkoutDTO workoutDTO);

    default Workout fromId(Long id) {
        if (id == null) {
            return null;
        }
        Workout workout = new Workout();
        workout.setId(id);
        return workout;
    }
}
