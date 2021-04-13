package fr.perform.service;

import fr.perform.service.dto.ExerciseTypeDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.perform.domain.ExerciseType}.
 */
public interface ExerciseTypeService {

    /**
     * Save a exerciseType.
     *
     * @param exerciseTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ExerciseTypeDTO save(ExerciseTypeDTO exerciseTypeDTO);

    /**
     * Get all the exerciseTypes.
     *
     * @return the list of entities.
     */
    List<ExerciseTypeDTO> findAll();


    /**
     * Get the "id" exerciseType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExerciseTypeDTO> findOne(Long id);

    /**
     * Delete the "id" exerciseType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
