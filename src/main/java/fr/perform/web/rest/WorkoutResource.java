package fr.perform.web.rest;

import fr.perform.service.WorkoutService;
import fr.perform.utils.wrappers.WrapperWorkout;
import fr.perform.web.rest.errors.BadRequestAlertException;
import fr.perform.service.dto.WorkoutDTO;

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
 * REST controller for managing {@link fr.perform.domain.Workout}.
 */
@RestController
@RequestMapping("/api")
public class WorkoutResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutResource.class);

    private static final String ENTITY_NAME = "workout";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkoutService workoutService;

    public WorkoutResource(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    /**
     * {@code POST  /workouts} : Create a new workout.
     *
     * @param workoutDTO the workoutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workoutDTO, or with status {@code 400 (Bad Request)} if the workout has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workouts")
    public ResponseEntity<WorkoutDTO> createWorkout(@Valid @RequestBody WorkoutDTO workoutDTO) throws URISyntaxException {
        log.debug("REST request to save Workout : {}", workoutDTO);
        if (workoutDTO.getId() != null) {
            throw new BadRequestAlertException("A new workout cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkoutDTO result = workoutService.save(workoutDTO);
        return ResponseEntity.created(new URI("/api/workouts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workouts} : Updates an existing workout.
     *
     * @param workoutDTO the workoutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workoutDTO,
     * or with status {@code 400 (Bad Request)} if the workoutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workoutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workouts")
    public ResponseEntity<WorkoutDTO> updateWorkout(@Valid @RequestBody WorkoutDTO workoutDTO) throws URISyntaxException {
        log.debug("REST request to update Workout : {}", workoutDTO);
        if (workoutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkoutDTO result = workoutService.save(workoutDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workoutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /workouts} : get all the workouts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workouts in body.
     */
    @GetMapping("/workouts")
    public List<WorkoutDTO> getAllWorkouts() {
        log.debug("REST request to get all Workouts");
        return workoutService.findAll();
    }

    /**
     * @author Jérémy Schrotzenberger
     *
     * {@code GET  /workouts/user/{userId}} : get all the workouts for a user.
     *
     * @param userId the id of the user, to retrieve his workouts.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workouts in body.
     */
    @GetMapping("/workouts/user/{userId}")
    public List<WorkoutDTO> findByUserId(@PathVariable Long userId) {
        log.debug("REST request to get all Workouts");
        return workoutService.findByUserId(userId);
    }

    /**
     * {@code GET  /workouts/:id} : get the "id" workout.
     *
     * @param id the id of the workoutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workoutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workouts/{id}")
    public ResponseEntity<WorkoutDTO> getWorkout(@PathVariable Long id) {
        log.debug("REST request to get Workout : {}", id);
        Optional<WorkoutDTO> workoutDTO = workoutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workoutDTO);
    }

    /**
     * {@code DELETE  /workouts/:id} : delete the "id" workout.
     *
     * @param id the id of the workoutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workouts/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        log.debug("REST request to delete Workout : {}", id);
        workoutService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * @author Jérémy Schrotzenberger.
     *
     * {@code GET /wrapperworkouts/workouts/:id} : get the wrapperWorkout from its id.
     *
     * @param id of the Workout
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the wrapperWorkout in body.
     */
    @GetMapping("/wrapperworkout/workout/{id}")
    public Optional<WrapperWorkout> findById(@PathVariable Long id) {
        log.debug("REST request to get the wrapperWorkout by workoutId:{}", id);
        return workoutService.findById(id);
    }
}
