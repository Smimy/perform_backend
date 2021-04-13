package fr.perform.service.mapper;


import fr.perform.domain.*;
import fr.perform.service.dto.ExerciseTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExerciseType} and its DTO {@link ExerciseTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExerciseTypeMapper extends EntityMapper<ExerciseTypeDTO, ExerciseType> {



    default ExerciseType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExerciseType exerciseType = new ExerciseType();
        exerciseType.setId(id);
        return exerciseType;
    }
}
