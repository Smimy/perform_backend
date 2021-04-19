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

    /**
     * @author Jérémy Schrotzenberger.
     *
     * @param userId the id of the user.
     * @return a list of Workouts linked to the id of the user.
     */
    @Query("SELECT w FROM Workout w " +
        "WHERE w.user.id=:userId " +
        "ORDER BY w.date DESC")
    List<Workout> findByUserId(@Param("userId") Long userId);
}
