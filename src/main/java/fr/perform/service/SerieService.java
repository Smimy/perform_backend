package fr.perform.service;

import fr.perform.service.dto.SerieDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.perform.domain.Serie}.
 */
public interface SerieService {

    /**
     * Save a serie.
     *
     * @param serieDTO the entity to save.
     * @return the persisted entity.
     */
    SerieDTO save(SerieDTO serieDTO);

    /**
     * Get all the series.
     *
     * @return the list of entities.
     */
    List<SerieDTO> findAll();


    /**
     * Get the "id" serie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SerieDTO> findOne(Long id);

    /**
     * Delete the "id" serie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * @author Jérémy Schrotzenberger.
     *
     * Get all series by id of the exercise.
     *
     * @param exerciseId the id of the exercise.
     * @return the list of entities.
     */
    List<SerieDTO> findAllByExerciseId(Long exerciseId);
}
