package fr.perform.repository;

import fr.perform.domain.Workout;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Workout entity.
 */

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @Query("select workout from Workout workout where workout.user.login = ?#{principal.username}")
    List<Workout> findByUserIsCurrentUser();

    @Query("select w from Workout w " +
        "where w.user.id=:userId order by w.date desc")
    List<Workout> findByUserId(@Param("userId") Long userId);
}
