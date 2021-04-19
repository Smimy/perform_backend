package fr.perform.repository;

import fr.perform.domain.Serie;

import fr.perform.service.dto.SerieDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Serie entity.
 */

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {

    /**
     * @author Jérémy Schrotzenberger.
     *
     * @param exerciseId the id of the exercise.
     * @return a list of all series linked to the id of the exercise.
     */
    @Query("FROM Serie S" +
        " WHERE S.exercise.id = :exerciseId")
    List<Serie> findAllByExerciseId(@Param(value = "exerciseId") Long exerciseId);
}
