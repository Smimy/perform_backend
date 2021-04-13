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

    @Query("FROM Exercise E" +
        " WHERE E.workout.id = :workoutId" +
        " ORDER BY E.number")
    List<Exercise> findAllExercisesByWorkoutId(@Param(value = "workoutId") Long workoutId);
}
