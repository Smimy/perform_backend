package fr.perform.service;

import fr.perform.service.dto.WorkoutGoalDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.perform.domain.WorkoutGoal}.
 */
public interface WorkoutGoalService {

    /**
     * Save a workoutGoal.
     *
     * @param workoutGoalDTO the entity to save.
     * @return the persisted entity.
     */
    WorkoutGoalDTO save(WorkoutGoalDTO workoutGoalDTO);

    /**
     * Get all the workoutGoals.
     *
     * @return the list of entities.
     */
    List<WorkoutGoalDTO> findAll();


    /**
     * Get the "id" workoutGoal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WorkoutGoalDTO> findOne(Long id);

    /**
     * Delete the "id" workoutGoal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
