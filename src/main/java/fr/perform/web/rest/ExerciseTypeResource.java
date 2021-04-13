package fr.perform.web.rest;

import fr.perform.service.ExerciseTypeService;
import fr.perform.web.rest.errors.BadRequestAlertException;
import fr.perform.service.dto.ExerciseTypeDTO;

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
 * REST controller for managing {@link fr.perform.domain.ExerciseType}.
 */
@RestController
@RequestMapping("/api")
public class ExerciseTypeResource {

    private final Logger log = LoggerFactory.getLogger(ExerciseTypeResource.class);

    private static final String ENTITY_NAME = "exerciseType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExerciseTypeService exerciseTypeService;

    public ExerciseTypeResource(ExerciseTypeService exerciseTypeService) {
        this.exerciseTypeService = exerciseTypeService;
    }

    /**
     * {@code POST  /exercise-types} : Create a new exerciseType.
     *
     * @param exerciseTypeDTO the exerciseTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exerciseTypeDTO, or with status {@code 400 (Bad Request)} if the exerciseType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exercise-types")
    public ResponseEntity<ExerciseTypeDTO> createExerciseType(@Valid @RequestBody ExerciseTypeDTO exerciseTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ExerciseType : {}", exerciseTypeDTO);
        if (exerciseTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new exerciseType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExerciseTypeDTO result = exerciseTypeService.save(exerciseTypeDTO);
        return ResponseEntity.created(new URI("/api/exercise-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exercise-types} : Updates an existing exerciseType.
     *
     * @param exerciseTypeDTO the exerciseTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exerciseTypeDTO,
     * or with status {@code 400 (Bad Request)} if the exerciseTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exerciseTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exercise-types")
    public ResponseEntity<ExerciseTypeDTO> updateExerciseType(@Valid @RequestBody ExerciseTypeDTO exerciseTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ExerciseType : {}", exerciseTypeDTO);
        if (exerciseTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExerciseTypeDTO result = exerciseTypeService.save(exerciseTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, exerciseTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exercise-types} : get all the exerciseTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exerciseTypes in body.
     */
    @GetMapping("/exercise-types")
    public List<ExerciseTypeDTO> getAllExerciseTypes() {
        log.debug("REST request to get all ExerciseTypes");
        return exerciseTypeService.findAll();
    }

    /**
     * {@code GET  /exercise-types/:id} : get the "id" exerciseType.
     *
     * @param id the id of the exerciseTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exerciseTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exercise-types/{id}")
    public ResponseEntity<ExerciseTypeDTO> getExerciseType(@PathVariable Long id) {
        log.debug("REST request to get ExerciseType : {}", id);
        Optional<ExerciseTypeDTO> exerciseTypeDTO = exerciseTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exerciseTypeDTO);
    }

    /**
     * {@code DELETE  /exercise-types/:id} : delete the "id" exerciseType.
     *
     * @param id the id of the exerciseTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exercise-types/{id}")
    public ResponseEntity<Void> deleteExerciseType(@PathVariable Long id) {
        log.debug("REST request to delete ExerciseType : {}", id);
        exerciseTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
