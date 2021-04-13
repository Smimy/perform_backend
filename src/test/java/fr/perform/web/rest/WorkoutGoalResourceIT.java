package fr.perform.web.rest;

import fr.perform.BackEndApp;
import fr.perform.domain.WorkoutGoal;
import fr.perform.repository.WorkoutGoalRepository;
import fr.perform.service.WorkoutGoalService;
import fr.perform.service.dto.WorkoutGoalDTO;
import fr.perform.service.mapper.WorkoutGoalMapper;

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
 * Integration tests for the {@link WorkoutGoalResource} REST controller.
 */
@SpringBootTest(classes = BackEndApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkoutGoalResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private WorkoutGoalRepository workoutGoalRepository;

    @Autowired
    private WorkoutGoalMapper workoutGoalMapper;

    @Autowired
    private WorkoutGoalService workoutGoalService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkoutGoalMockMvc;

    private WorkoutGoal workoutGoal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkoutGoal createEntity(EntityManager em) {
        WorkoutGoal workoutGoal = new WorkoutGoal()
            .name(DEFAULT_NAME);
        return workoutGoal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkoutGoal createUpdatedEntity(EntityManager em) {
        WorkoutGoal workoutGoal = new WorkoutGoal()
            .name(UPDATED_NAME);
        return workoutGoal;
    }

    @BeforeEach
    public void initTest() {
        workoutGoal = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkoutGoal() throws Exception {
        int databaseSizeBeforeCreate = workoutGoalRepository.findAll().size();
        // Create the WorkoutGoal
        WorkoutGoalDTO workoutGoalDTO = workoutGoalMapper.toDto(workoutGoal);
        restWorkoutGoalMockMvc.perform(post("/api/workout-goals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutGoalDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkoutGoal in the database
        List<WorkoutGoal> workoutGoalList = workoutGoalRepository.findAll();
        assertThat(workoutGoalList).hasSize(databaseSizeBeforeCreate + 1);
        WorkoutGoal testWorkoutGoal = workoutGoalList.get(workoutGoalList.size() - 1);
        assertThat(testWorkoutGoal.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createWorkoutGoalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workoutGoalRepository.findAll().size();

        // Create the WorkoutGoal with an existing ID
        workoutGoal.setId(1L);
        WorkoutGoalDTO workoutGoalDTO = workoutGoalMapper.toDto(workoutGoal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkoutGoalMockMvc.perform(post("/api/workout-goals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutGoalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkoutGoal in the database
        List<WorkoutGoal> workoutGoalList = workoutGoalRepository.findAll();
        assertThat(workoutGoalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutGoalRepository.findAll().size();
        // set the field null
        workoutGoal.setName(null);

        // Create the WorkoutGoal, which fails.
        WorkoutGoalDTO workoutGoalDTO = workoutGoalMapper.toDto(workoutGoal);


        restWorkoutGoalMockMvc.perform(post("/api/workout-goals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutGoalDTO)))
            .andExpect(status().isBadRequest());

        List<WorkoutGoal> workoutGoalList = workoutGoalRepository.findAll();
        assertThat(workoutGoalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkoutGoals() throws Exception {
        // Initialize the database
        workoutGoalRepository.saveAndFlush(workoutGoal);

        // Get all the workoutGoalList
        restWorkoutGoalMockMvc.perform(get("/api/workout-goals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workoutGoal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getWorkoutGoal() throws Exception {
        // Initialize the database
        workoutGoalRepository.saveAndFlush(workoutGoal);

        // Get the workoutGoal
        restWorkoutGoalMockMvc.perform(get("/api/workout-goals/{id}", workoutGoal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workoutGoal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingWorkoutGoal() throws Exception {
        // Get the workoutGoal
        restWorkoutGoalMockMvc.perform(get("/api/workout-goals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkoutGoal() throws Exception {
        // Initialize the database
        workoutGoalRepository.saveAndFlush(workoutGoal);

        int databaseSizeBeforeUpdate = workoutGoalRepository.findAll().size();

        // Update the workoutGoal
        WorkoutGoal updatedWorkoutGoal = workoutGoalRepository.findById(workoutGoal.getId()).get();
        // Disconnect from session so that the updates on updatedWorkoutGoal are not directly saved in db
        em.detach(updatedWorkoutGoal);
        updatedWorkoutGoal
            .name(UPDATED_NAME);
        WorkoutGoalDTO workoutGoalDTO = workoutGoalMapper.toDto(updatedWorkoutGoal);

        restWorkoutGoalMockMvc.perform(put("/api/workout-goals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutGoalDTO)))
            .andExpect(status().isOk());

        // Validate the WorkoutGoal in the database
        List<WorkoutGoal> workoutGoalList = workoutGoalRepository.findAll();
        assertThat(workoutGoalList).hasSize(databaseSizeBeforeUpdate);
        WorkoutGoal testWorkoutGoal = workoutGoalList.get(workoutGoalList.size() - 1);
        assertThat(testWorkoutGoal.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkoutGoal() throws Exception {
        int databaseSizeBeforeUpdate = workoutGoalRepository.findAll().size();

        // Create the WorkoutGoal
        WorkoutGoalDTO workoutGoalDTO = workoutGoalMapper.toDto(workoutGoal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkoutGoalMockMvc.perform(put("/api/workout-goals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutGoalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkoutGoal in the database
        List<WorkoutGoal> workoutGoalList = workoutGoalRepository.findAll();
        assertThat(workoutGoalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkoutGoal() throws Exception {
        // Initialize the database
        workoutGoalRepository.saveAndFlush(workoutGoal);

        int databaseSizeBeforeDelete = workoutGoalRepository.findAll().size();

        // Delete the workoutGoal
        restWorkoutGoalMockMvc.perform(delete("/api/workout-goals/{id}", workoutGoal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkoutGoal> workoutGoalList = workoutGoalRepository.findAll();
        assertThat(workoutGoalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
