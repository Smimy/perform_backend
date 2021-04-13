package fr.perform.web.rest;

import fr.perform.BackEndApp;
import fr.perform.domain.Serie;
import fr.perform.domain.Exercise;
import fr.perform.repository.SerieRepository;
import fr.perform.service.SerieService;
import fr.perform.service.dto.SerieDTO;
import fr.perform.service.mapper.SerieMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SerieResource} REST controller.
 */
@SpringBootTest(classes = BackEndApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SerieResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final Integer DEFAULT_REPS_ACHIEVED = 1;
    private static final Integer UPDATED_REPS_ACHIEVED = 2;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final Float DEFAULT_PERCENT_1_RM = 1F;
    private static final Float UPDATED_PERCENT_1_RM = 2F;

    private static final Integer DEFAULT_RESTING_TIME = 1;
    private static final Integer UPDATED_RESTING_TIME = 2;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private SerieMapper serieMapper;

    @Autowired
    private SerieService serieService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSerieMockMvc;

    private Serie serie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Serie createEntity(EntityManager em) {
        Serie serie = new Serie()
            .number(DEFAULT_NUMBER)
            .repsAchieved(DEFAULT_REPS_ACHIEVED)
            .weight(DEFAULT_WEIGHT)
            .percent1RM(DEFAULT_PERCENT_1_RM)
            .restingTime(DEFAULT_RESTING_TIME);
        // Add required entity
        Exercise exercise;
        if (TestUtil.findAll(em, Exercise.class).isEmpty()) {
            exercise = ExerciseResourceIT.createEntity(em);
            em.persist(exercise);
            em.flush();
        } else {
            exercise = TestUtil.findAll(em, Exercise.class).get(0);
        }
        serie.setExercise(exercise);
        return serie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Serie createUpdatedEntity(EntityManager em) {
        Serie serie = new Serie()
            .number(UPDATED_NUMBER)
            .repsAchieved(UPDATED_REPS_ACHIEVED)
            .weight(UPDATED_WEIGHT)
            .percent1RM(UPDATED_PERCENT_1_RM)
            .restingTime(UPDATED_RESTING_TIME);
        // Add required entity
        Exercise exercise;
        if (TestUtil.findAll(em, Exercise.class).isEmpty()) {
            exercise = ExerciseResourceIT.createUpdatedEntity(em);
            em.persist(exercise);
            em.flush();
        } else {
            exercise = TestUtil.findAll(em, Exercise.class).get(0);
        }
        serie.setExercise(exercise);
        return serie;
    }

    @BeforeEach
    public void initTest() {
        serie = createEntity(em);
    }

    @Test
    @Transactional
    public void createSerie() throws Exception {
        int databaseSizeBeforeCreate = serieRepository.findAll().size();
        // Create the Serie
        SerieDTO serieDTO = serieMapper.toDto(serie);
        restSerieMockMvc.perform(post("/api/series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serieDTO)))
            .andExpect(status().isCreated());

        // Validate the Serie in the database
        List<Serie> serieList = serieRepository.findAll();
        assertThat(serieList).hasSize(databaseSizeBeforeCreate + 1);
        Serie testSerie = serieList.get(serieList.size() - 1);
        assertThat(testSerie.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testSerie.getRepsAchieved()).isEqualTo(DEFAULT_REPS_ACHIEVED);
        assertThat(testSerie.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testSerie.getPercent1RM()).isEqualTo(DEFAULT_PERCENT_1_RM);
        assertThat(testSerie.getRestingTime()).isEqualTo(DEFAULT_RESTING_TIME);
    }

    @Test
    @Transactional
    public void createSerieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serieRepository.findAll().size();

        // Create the Serie with an existing ID
        serie.setId(1L);
        SerieDTO serieDTO = serieMapper.toDto(serie);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSerieMockMvc.perform(post("/api/series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Serie in the database
        List<Serie> serieList = serieRepository.findAll();
        assertThat(serieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = serieRepository.findAll().size();
        // set the field null
        serie.setNumber(null);

        // Create the Serie, which fails.
        SerieDTO serieDTO = serieMapper.toDto(serie);


        restSerieMockMvc.perform(post("/api/series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serieDTO)))
            .andExpect(status().isBadRequest());

        List<Serie> serieList = serieRepository.findAll();
        assertThat(serieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepsAchievedIsRequired() throws Exception {
        int databaseSizeBeforeTest = serieRepository.findAll().size();
        // set the field null
        serie.setRepsAchieved(null);

        // Create the Serie, which fails.
        SerieDTO serieDTO = serieMapper.toDto(serie);


        restSerieMockMvc.perform(post("/api/series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serieDTO)))
            .andExpect(status().isBadRequest());

        List<Serie> serieList = serieRepository.findAll();
        assertThat(serieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = serieRepository.findAll().size();
        // set the field null
        serie.setWeight(null);

        // Create the Serie, which fails.
        SerieDTO serieDTO = serieMapper.toDto(serie);


        restSerieMockMvc.perform(post("/api/series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serieDTO)))
            .andExpect(status().isBadRequest());

        List<Serie> serieList = serieRepository.findAll();
        assertThat(serieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSeries() throws Exception {
        // Initialize the database
        serieRepository.saveAndFlush(serie);

        // Get all the serieList
        restSerieMockMvc.perform(get("/api/series?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serie.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].repsAchieved").value(hasItem(DEFAULT_REPS_ACHIEVED)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].percent1RM").value(hasItem(DEFAULT_PERCENT_1_RM.doubleValue())))
            .andExpect(jsonPath("$.[*].restingTime").value(hasItem(DEFAULT_RESTING_TIME)));
    }
    
    @Test
    @Transactional
    public void getSerie() throws Exception {
        // Initialize the database
        serieRepository.saveAndFlush(serie);

        // Get the serie
        restSerieMockMvc.perform(get("/api/series/{id}", serie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serie.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.repsAchieved").value(DEFAULT_REPS_ACHIEVED))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.percent1RM").value(DEFAULT_PERCENT_1_RM.doubleValue()))
            .andExpect(jsonPath("$.restingTime").value(DEFAULT_RESTING_TIME));
    }
    @Test
    @Transactional
    public void getNonExistingSerie() throws Exception {
        // Get the serie
        restSerieMockMvc.perform(get("/api/series/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSerie() throws Exception {
        // Initialize the database
        serieRepository.saveAndFlush(serie);

        int databaseSizeBeforeUpdate = serieRepository.findAll().size();

        // Update the serie
        Serie updatedSerie = serieRepository.findById(serie.getId()).get();
        // Disconnect from session so that the updates on updatedSerie are not directly saved in db
        em.detach(updatedSerie);
        updatedSerie
            .number(UPDATED_NUMBER)
            .repsAchieved(UPDATED_REPS_ACHIEVED)
            .weight(UPDATED_WEIGHT)
            .percent1RM(UPDATED_PERCENT_1_RM)
            .restingTime(UPDATED_RESTING_TIME);
        SerieDTO serieDTO = serieMapper.toDto(updatedSerie);

        restSerieMockMvc.perform(put("/api/series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serieDTO)))
            .andExpect(status().isOk());

        // Validate the Serie in the database
        List<Serie> serieList = serieRepository.findAll();
        assertThat(serieList).hasSize(databaseSizeBeforeUpdate);
        Serie testSerie = serieList.get(serieList.size() - 1);
        assertThat(testSerie.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testSerie.getRepsAchieved()).isEqualTo(UPDATED_REPS_ACHIEVED);
        assertThat(testSerie.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testSerie.getPercent1RM()).isEqualTo(UPDATED_PERCENT_1_RM);
        assertThat(testSerie.getRestingTime()).isEqualTo(UPDATED_RESTING_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSerie() throws Exception {
        int databaseSizeBeforeUpdate = serieRepository.findAll().size();

        // Create the Serie
        SerieDTO serieDTO = serieMapper.toDto(serie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSerieMockMvc.perform(put("/api/series")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Serie in the database
        List<Serie> serieList = serieRepository.findAll();
        assertThat(serieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSerie() throws Exception {
        // Initialize the database
        serieRepository.saveAndFlush(serie);

        int databaseSizeBeforeDelete = serieRepository.findAll().size();

        // Delete the serie
        restSerieMockMvc.perform(delete("/api/series/{id}", serie.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Serie> serieList = serieRepository.findAll();
        assertThat(serieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
