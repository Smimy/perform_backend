package fr.perform.web.rest;

import fr.perform.BackEndApp;
import fr.perform.domain.Workout;
import fr.perform.domain.WorkoutGoal;
import fr.perform.domain.User;
import fr.perform.repository.WorkoutRepository;
import fr.perform.service.WorkoutService;
import fr.perform.service.dto.WorkoutDTO;
import fr.perform.service.mapper.WorkoutMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkoutResource} REST controller.
 */
@SpringBootTest(classes = BackEndApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkoutResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutMapper workoutMapper;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkoutMockMvc;

    private Workout workout;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workout createEntity(EntityManager em) {
        Workout workout = new Workout()
            .name(DEFAULT_NAME)
            .date(DEFAULT_DATE)
            .comment(DEFAULT_COMMENT);
        // Add required entity
        WorkoutGoal workoutGoal;
        if (TestUtil.findAll(em, WorkoutGoal.class).isEmpty()) {
            workoutGoal = WorkoutGoalResourceIT.createEntity(em);
            em.persist(workoutGoal);
            em.flush();
        } else {
            workoutGoal = TestUtil.findAll(em, WorkoutGoal.class).get(0);
        }
        workout.setWorkoutGoal(workoutGoal);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        workout.setUser(user);
        return workout;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workout createUpdatedEntity(EntityManager em) {
        Workout workout = new Workout()
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .comment(UPDATED_COMMENT);
        // Add required entity
        WorkoutGoal workoutGoal;
        if (TestUtil.findAll(em, WorkoutGoal.class).isEmpty()) {
            workoutGoal = WorkoutGoalResourceIT.createUpdatedEntity(em);
            em.persist(workoutGoal);
            em.flush();
        } else {
            workoutGoal = TestUtil.findAll(em, WorkoutGoal.class).get(0);
        }
        workout.setWorkoutGoal(workoutGoal);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        workout.setUser(user);
        return workout;
    }

    @BeforeEach
    public void initTest() {
        workout = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkout() throws Exception {
        int databaseSizeBeforeCreate = workoutRepository.findAll().size();
        // Create the Workout
        WorkoutDTO workoutDTO = workoutMapper.toDto(workout);
        restWorkoutMockMvc.perform(post("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutDTO)))
            .andExpect(status().isCreated());

        // Validate the Workout in the database
        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeCreate + 1);
        Workout testWorkout = workoutList.get(workoutList.size() - 1);
        assertThat(testWorkout.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkout.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testWorkout.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void createWorkoutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workoutRepository.findAll().size();

        // Create the Workout with an existing ID
        workout.setId(1L);
        WorkoutDTO workoutDTO = workoutMapper.toDto(workout);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkoutMockMvc.perform(post("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Workout in the database
        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutRepository.findAll().size();
        // set the field null
        workout.setName(null);

        // Create the Workout, which fails.
        WorkoutDTO workoutDTO = workoutMapper.toDto(workout);


        restWorkoutMockMvc.perform(post("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutDTO)))
            .andExpect(status().isBadRequest());

        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutRepository.findAll().size();
        // set the field null
        workout.setDate(null);

        // Create the Workout, which fails.
        WorkoutDTO workoutDTO = workoutMapper.toDto(workout);


        restWorkoutMockMvc.perform(post("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutDTO)))
            .andExpect(status().isBadRequest());

        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkouts() throws Exception {
        // Initialize the database
        workoutRepository.saveAndFlush(workout);

        // Get all the workoutList
        restWorkoutMockMvc.perform(get("/api/workouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workout.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)));
    }
    
    @Test
    @Transactional
    public void getWorkout() throws Exception {
        // Initialize the database
        workoutRepository.saveAndFlush(workout);

        // Get the workout
        restWorkoutMockMvc.perform(get("/api/workouts/{id}", workout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workout.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT));
    }
    @Test
    @Transactional
    public void getNonExistingWorkout() throws Exception {
        // Get the workout
        restWorkoutMockMvc.perform(get("/api/workouts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkout() throws Exception {
        // Initialize the database
        workoutRepository.saveAndFlush(workout);

        int databaseSizeBeforeUpdate = workoutRepository.findAll().size();

        // Update the workout
        Workout updatedWorkout = workoutRepository.findById(workout.getId()).get();
        // Disconnect from session so that the updates on updatedWorkout are not directly saved in db
        em.detach(updatedWorkout);
        updatedWorkout
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .comment(UPDATED_COMMENT);
        WorkoutDTO workoutDTO = workoutMapper.toDto(updatedWorkout);

        restWorkoutMockMvc.perform(put("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutDTO)))
            .andExpect(status().isOk());

        // Validate the Workout in the database
        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeUpdate);
        Workout testWorkout = workoutList.get(workoutList.size() - 1);
        assertThat(testWorkout.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkout.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testWorkout.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkout() throws Exception {
        int databaseSizeBeforeUpdate = workoutRepository.findAll().size();

        // Create the Workout
        WorkoutDTO workoutDTO = workoutMapper.toDto(workout);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkoutMockMvc.perform(put("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workoutDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Workout in the database
        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkout() throws Exception {
        // Initialize the database
        workoutRepository.saveAndFlush(workout);

        int databaseSizeBeforeDelete = workoutRepository.findAll().size();

        // Delete the workout
        restWorkoutMockMvc.perform(delete("/api/workouts/{id}", workout.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
