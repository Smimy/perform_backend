package fr.perform.service;

import fr.perform.service.dto.ExerciseDTO;
import fr.perform.utils.wrappers.WrapperExercise;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.perform.domain.Exercise}.
 */
public interface ExerciseService {

    /**
     * Save a exercise.
     *
     * @param exerciseDTO the entity to save.
     * @return the persisted entity.
     */
    ExerciseDTO save(ExerciseDTO exerciseDTO);

    /**
     * Get all the exercises.
     *
     * @return the list of entities.
     */
    List<ExerciseDTO> findAll();


    /**
     * Get the "id" exercise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExerciseDTO> findOne(Long id);

    /**
     * Delete the "id" exercise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    /**
     * @author Jérémy Schrotzenberger.
     *
     * Get the wrapperExercise from id of the Exercise.
     *
     * @param id the id of the workout.
     * @return the entity.
     */
    Optional<WrapperExercise> findWrapperExerciseById(Long id);

    /**
     * @author Jérémy Schrotzenberger.
     *
     * Get all wrapperExercise.
     *
     * @param workoutId the id of the workout.
     * @return the list of entities.
     */
    List<WrapperExercise> findAllWrapperExerciseByWorkoutId(Long workoutId);
}
