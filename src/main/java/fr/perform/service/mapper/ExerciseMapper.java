package fr.perform.service.mapper;


import fr.perform.domain.*;
import fr.perform.service.dto.ExerciseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Exercise} and its DTO {@link ExerciseDTO}.
 */
@Mapper(componentModel = "spring", uses = {WorkoutMapper.class, ExerciseTypeMapper.class})
public interface ExerciseMapper extends EntityMapper<ExerciseDTO, Exercise> {

    @Mapping(source = "workout.id", target = "workoutId")
    @Mapping(source = "exerciseType.id", target = "exerciseTypeId")
    ExerciseDTO toDto(Exercise exercise);

    @Mapping(source = "workoutId", target = "workout")
    @Mapping(source = "exerciseTypeId", target = "exerciseType")
    Exercise toEntity(ExerciseDTO exerciseDTO);

    default Exercise fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exercise exercise = new Exercise();
        exercise.setId(id);
        return exercise;
    }
}
