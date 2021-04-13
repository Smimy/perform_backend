package fr.perform.repository;

import fr.perform.domain.WorkoutGoal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkoutGoal entity.
 */

@Repository
public interface WorkoutGoalRepository extends JpaRepository<WorkoutGoal, Long> {
}
