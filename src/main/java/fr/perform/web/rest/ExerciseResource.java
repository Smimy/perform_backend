package fr.perform.web.rest;

import fr.perform.service.ExerciseService;
import fr.perform.utils.wrappers.WrapperExercise;
import fr.perform.web.rest.errors.BadRequestAlertException;
import fr.perform.service.dto.ExerciseDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.perform.domain.Exercise}.
 */
@RestController
@RequestMapping("/api")
public class ExerciseResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseResource.class);

    private static final String ENTITY_NAME = "exercise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExerciseService exerciseService;

    public ExerciseResource(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    /**
     * {@code POST  /exercises} : Create a new exercise.
     *
     * @param exerciseDTO the exerciseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exerciseDTO, or with status {@code 400 (Bad Request)} if the exercise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exercises")
    public ResponseEntity<ExerciseDTO> createExercise(@Valid @RequestBody ExerciseDTO exerciseDTO) throws URISyntaxException {
        log.debug("REST request to save Exercise : {}", exerciseDTO);
        if (exerciseDTO.getId() != null) {
            throw new BadRequestAlertException("A new exercise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExerciseDTO result = exerciseService.save(exerciseDTO);
        return ResponseEntity.created(new URI("/api/exercises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exercises} : Updates an existing exercise.
     *
     * @param exerciseDTO the exerciseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exerciseDTO,
     * or with status {@code 400 (Bad Request)} if the exerciseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exerciseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exercises")
    public ResponseEntity<ExerciseDTO> updateExercise(@Valid @RequestBody ExerciseDTO exerciseDTO) throws URISyntaxException {
        log.debug("REST request to update Exercise : {}", exerciseDTO);
        if (exerciseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExerciseDTO result = exerciseService.save(exerciseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, exerciseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exercises} : get all the exercises.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exercises in body.
     */
    @GetMapping("/exercises")
    public List<ExerciseDTO> getAllExercises() {
        log.debug("REST request to get all Exercises");
        return exerciseService.findAll();
    }

    /**
     * {@code GET  /exercises/:id} : get the "id" exercise.
     *
     * @param id the id of the exerciseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exerciseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exercises/{id}")
    public ResponseEntity<ExerciseDTO> getExercise(@PathVariable Long id) {
        log.debug("REST request to get Exercise : {}", id);
        Optional<ExerciseDTO> exerciseDTO = exerciseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exerciseDTO);
    }

    /**
     * {@code DELETE  /exercises/:id} : delete the "id" exercise.
     *
     * @param id the id of the exerciseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exercises/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        log.debug("REST request to delete Exercise : {}", id);
        exerciseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET /wrapperexercises/exercise/:id} : get the wrapperExercise from the id of the Exercise.
     *
     * @param id id of the Exercise
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the wrapperExercise in body
     */
    @GetMapping("/wrapperexercises/exercise/{id}")
    public Optional<WrapperExercise> findWrapperExerciseById(@PathVariable Long id) {
        log.debug("REST request to get all WrapperExercise by workoutId:{}", id);
        return exerciseService.findWrapperExerciseById(id);
    }

    /**
     * {@code GET /wrapperexercises/workout/:workoutId} : get all wrapperExercise from the id of the Workout.
     *
     * @param workoutId id of the Workout
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and all wrapperExercise in body
     */
    @GetMapping("/wrapperexercises/workout/{workoutId}")
    public List<WrapperExercise> findAllWrapperExerciseByWorkoutId(@PathVariable Long workoutId) {
        log.debug("REST request to get all WrapperExercise by workoutId:{}", workoutId);
        return exerciseService.findAllWrapperExerciseByWorkoutId(workoutId);
    }
}
