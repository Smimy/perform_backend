package fr.perform.service.mapper;


import fr.perform.domain.*;
import fr.perform.service.dto.SerieDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Serie} and its DTO {@link SerieDTO}.
 */
@Mapper(componentModel = "spring", uses = {ExerciseMapper.class})
public interface SerieMapper extends EntityMapper<SerieDTO, Serie> {

    @Mapping(source = "exercise.id", target = "exerciseId")
    SerieDTO toDto(Serie serie);

    @Mapping(source = "exerciseId", target = "exercise")
    Serie toEntity(SerieDTO serieDTO);

    default Serie fromId(Long id) {
        if (id == null) {
            return null;
        }
        Serie serie = new Serie();
        serie.setId(id);
        return serie;
    }
}
