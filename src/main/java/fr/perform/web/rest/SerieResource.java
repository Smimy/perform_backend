package fr.perform.web.rest;

import fr.perform.service.SerieService;
import fr.perform.web.rest.errors.BadRequestAlertException;
import fr.perform.service.dto.SerieDTO;

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
 * REST controller for managing {@link fr.perform.domain.Serie}.
 */
@RestController
@RequestMapping("/api")
public class SerieResource {

    private final Logger log = LoggerFactory.getLogger(SerieResource.class);

    private static final String ENTITY_NAME = "serie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SerieService serieService;

    public SerieResource(SerieService serieService) {
        this.serieService = serieService;
    }

    /**
     * {@code POST  /series} : Create a new serie.
     *
     * @param serieDTO the serieDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serieDTO, or with status {@code 400 (Bad Request)} if the serie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/series")
    public ResponseEntity<SerieDTO> createSerie(@Valid @RequestBody SerieDTO serieDTO) throws URISyntaxException {
        log.debug("REST request to save Serie : {}", serieDTO);
        if (serieDTO.getId() != null) {
            throw new BadRequestAlertException("A new serie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SerieDTO result = serieService.save(serieDTO);
        return ResponseEntity.created(new URI("/api/series/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /series} : Updates an existing serie.
     *
     * @param serieDTO the serieDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serieDTO,
     * or with status {@code 400 (Bad Request)} if the serieDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serieDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/series")
    public ResponseEntity<SerieDTO> updateSerie(@Valid @RequestBody SerieDTO serieDTO) throws URISyntaxException {
        log.debug("REST request to update Serie : {}", serieDTO);
        if (serieDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SerieDTO result = serieService.save(serieDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serieDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /series} : get all the series.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of series in body.
     */
    @GetMapping("/series")
    public List<SerieDTO> getAllSeries() {
        log.debug("REST request to get all Series");
        return serieService.findAll();
    }

    /**
     * {@code GET  /series/:id} : get the "id" serie.
     *
     * @param id the id of the serieDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serieDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/series/{id}")
    public ResponseEntity<SerieDTO> getSerie(@PathVariable Long id) {
        log.debug("REST request to get Serie : {}", id);
        Optional<SerieDTO> serieDTO = serieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serieDTO);
    }

    /**
     * {@code DELETE  /series/:id} : delete the "id" serie.
     *
     * @param id the id of the serieDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/series/{id}")
    public ResponseEntity<Void> deleteSerie(@PathVariable Long id) {
        log.debug("REST request to delete Serie : {}", id);
        serieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /series/exercise/:exerciseId} : get all series by id of the exercise.
     *
     * @param exerciseId the id of the exercise.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of series in body.
     */
    @GetMapping("/series/exercise/{exerciseId}")
    public List<SerieDTO> findAllByExerciseId(@PathVariable Long exerciseId) {
        log.debug("REST request to get all Series by exerciseId {}", exerciseId);
        return serieService.findAllByExerciseId(exerciseId);
    }
}
