package fr.perform.repository;

import fr.perform.domain.ExerciseType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ExerciseType entity.
 */

@Repository
public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {
}
