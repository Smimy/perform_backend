package fr.perform.web.rest;

import fr.perform.service.WorkoutGoalService;
import fr.perform.web.rest.errors.BadRequestAlertException;
import fr.perform.service.dto.WorkoutGoalDTO;

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
 * REST controller for managing {@link fr.perform.domain.WorkoutGoal}.
 */
@RestController
@RequestMapping("/api")
public class WorkoutGoalResource {

    private final Logger log = LoggerFactory.getLogger(WorkoutGoalResource.class);

    private static final String ENTITY_NAME = "workoutGoal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkoutGoalService workoutGoalService;

    public WorkoutGoalResource(WorkoutGoalService workoutGoalService) {
        this.workoutGoalService = workoutGoalService;
    }

    /**
     * {@code POST  /workout-goals} : Create a new workoutGoal.
     *
     * @param workoutGoalDTO the workoutGoalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workoutGoalDTO, or with status {@code 400 (Bad Request)} if the workoutGoal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/workout-goals")
    public ResponseEntity<WorkoutGoalDTO> createWorkoutGoal(@Valid @RequestBody WorkoutGoalDTO workoutGoalDTO) throws URISyntaxException {
        log.debug("REST request to save WorkoutGoal : {}", workoutGoalDTO);
        if (workoutGoalDTO.getId() != null) {
            throw new BadRequestAlertException("A new workoutGoal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkoutGoalDTO result = workoutGoalService.save(workoutGoalDTO);
        return ResponseEntity.created(new URI("/api/workout-goals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /workout-goals} : Updates an existing workoutGoal.
     *
     * @param workoutGoalDTO the workoutGoalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workoutGoalDTO,
     * or with status {@code 400 (Bad Request)} if the workoutGoalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workoutGoalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/workout-goals")
    public ResponseEntity<WorkoutGoalDTO> updateWorkoutGoal(@Valid @RequestBody WorkoutGoalDTO workoutGoalDTO) throws URISyntaxException {
        log.debug("REST request to update WorkoutGoal : {}", workoutGoalDTO);
        if (workoutGoalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkoutGoalDTO result = workoutGoalService.save(workoutGoalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, workoutGoalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /workout-goals} : get all the workoutGoals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workoutGoals in body.
     */
    @GetMapping("/workout-goals")
    public List<WorkoutGoalDTO> getAllWorkoutGoals() {
        log.debug("REST request to get all WorkoutGoals");
        return workoutGoalService.findAll();
    }

    /**
     * {@code GET  /workout-goals/:id} : get the "id" workoutGoal.
     *
     * @param id the id of the workoutGoalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workoutGoalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/workout-goals/{id}")
    public ResponseEntity<WorkoutGoalDTO> getWorkoutGoal(@PathVariable Long id) {
        log.debug("REST request to get WorkoutGoal : {}", id);
        Optional<WorkoutGoalDTO> workoutGoalDTO = workoutGoalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workoutGoalDTO);
    }

    /**
     * {@code DELETE  /workout-goals/:id} : delete the "id" workoutGoal.
     *
     * @param id the id of the workoutGoalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/workout-goals/{id}")
    public ResponseEntity<Void> deleteWorkoutGoal(@PathVariable Long id) {
        log.debug("REST request to delete WorkoutGoal : {}", id);
        workoutGoalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
