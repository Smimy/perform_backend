package fr.perform.service;

import fr.perform.service.dto.WorkoutDTO;
import fr.perform.utils.wrappers.WrapperWorkout;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.perform.domain.Workout}.
 */
public interface WorkoutService {

    /**
     * Save a workout.
     *
     * @param workoutDTO the entity to save.
     * @return the persisted entity.
     */
    WorkoutDTO save(WorkoutDTO workoutDTO);

    /**
     * Get all the workouts.
     *
     * @return the list of entities.
     */
    List<WorkoutDTO> findAll();

    /**
     * Get the "id" workout.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkoutDTO> findOne(Long id);

    /**
     * Delete the "id" workout.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * @author Jérémy Schrotzenberger.
     *
     * Get all the workouts from userId.
     *
     * @param userId the id of the user.
     * @return the list of entities.
     */
    List<WorkoutDTO> findByUserId(Long userId);

    /**
     * Get the WrapperWorkout by its id
     *
     * @param id the id of the Workout
     * @return the wrapperWorkout
     */
    Optional<WrapperWorkout> findById(Long id);
}
