package fr.perform.repository;

import fr.perform.domain.Exercise;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Exercise entity.
 */

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    /**
     * @author Jérémy Schrotzenberger.
     *
     * @param workoutId the id of the workout.
     * @return a list of all exercises linked to the id of the workout.
     */
    @Query("FROM Exercise E" +
        " WHERE E.workout.id = :workoutId" +
        " ORDER BY E.number")
    List<Exercise> findAllExercisesByWorkoutId(@Param(value = "workoutId") Long workoutId);
}
