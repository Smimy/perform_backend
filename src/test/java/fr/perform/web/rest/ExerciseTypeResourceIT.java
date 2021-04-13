package fr.perform.web.rest;

import fr.perform.BackEndApp;
import fr.perform.domain.ExerciseType;
import fr.perform.repository.ExerciseTypeRepository;
import fr.perform.service.ExerciseTypeService;
import fr.perform.service.dto.ExerciseTypeDTO;
import fr.perform.service.mapper.ExerciseTypeMapper;

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
 * Integration tests for the {@link ExerciseTypeResource} REST controller.
 */
@SpringBootTest(classes = BackEndApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExerciseTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    @Autowired
    private ExerciseTypeMapper exerciseTypeMapper;

    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExerciseTypeMockMvc;

    private ExerciseType exerciseType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExerciseType createEntity(EntityManager em) {
        ExerciseType exerciseType = new ExerciseType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return exerciseType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExerciseType createUpdatedEntity(EntityManager em) {
        ExerciseType exerciseType = new ExerciseType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return exerciseType;
    }

    @BeforeEach
    public void initTest() {
        exerciseType = createEntity(em);
    }

    @Test
    @Transactional
    public void createExerciseType() throws Exception {
        int databaseSizeBeforeCreate = exerciseTypeRepository.findAll().size();
        // Create the ExerciseType
        ExerciseTypeDTO exerciseTypeDTO = exerciseTypeMapper.toDto(exerciseType);
        restExerciseTypeMockMvc.perform(post("/api/exercise-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exerciseTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ExerciseType in the database
        List<ExerciseType> exerciseTypeList = exerciseTypeRepository.findAll();
        assertThat(exerciseTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ExerciseType testExerciseType = exerciseTypeList.get(exerciseTypeList.size() - 1);
        assertThat(testExerciseType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExerciseType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createExerciseTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exerciseTypeRepository.findAll().size();

        // Create the ExerciseType with an existing ID
        exerciseType.setId(1L);
        ExerciseTypeDTO exerciseTypeDTO = exerciseTypeMapper.toDto(exerciseType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExerciseTypeMockMvc.perform(post("/api/exercise-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exerciseTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExerciseType in the database
        List<ExerciseType> exerciseTypeList = exerciseTypeRepository.findAll();
        assertThat(exerciseTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = exerciseTypeRepository.findAll().size();
        // set the field null
        exerciseType.setName(null);

        // Create the ExerciseType, which fails.
        ExerciseTypeDTO exerciseTypeDTO = exerciseTypeMapper.toDto(exerciseType);


        restExerciseTypeMockMvc.perform(post("/api/exercise-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exerciseTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ExerciseType> exerciseTypeList = exerciseTypeRepository.findAll();
        assertThat(exerciseTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExerciseTypes() throws Exception {
        // Initialize the database
        exerciseTypeRepository.saveAndFlush(exerciseType);

        // Get all the exerciseTypeList
        restExerciseTypeMockMvc.perform(get("/api/exercise-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exerciseType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getExerciseType() throws Exception {
        // Initialize the database
        exerciseTypeRepository.saveAndFlush(exerciseType);

        // Get the exerciseType
        restExerciseTypeMockMvc.perform(get("/api/exercise-types/{id}", exerciseType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exerciseType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingExerciseType() throws Exception {
        // Get the exerciseType
        restExerciseTypeMockMvc.perform(get("/api/exercise-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExerciseType() throws Exception {
        // Initialize the database
        exerciseTypeRepository.saveAndFlush(exerciseType);

        int databaseSizeBeforeUpdate = exerciseTypeRepository.findAll().size();

        // Update the exerciseType
        ExerciseType updatedExerciseType = exerciseTypeRepository.findById(exerciseType.getId()).get();
        // Disconnect from session so that the updates on updatedExerciseType are not directly saved in db
        em.detach(updatedExerciseType);
        updatedExerciseType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ExerciseTypeDTO exerciseTypeDTO = exerciseTypeMapper.toDto(updatedExerciseType);

        restExerciseTypeMockMvc.perform(put("/api/exercise-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exerciseTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ExerciseType in the database
        List<ExerciseType> exerciseTypeList = exerciseTypeRepository.findAll();
        assertThat(exerciseTypeList).hasSize(databaseSizeBeforeUpdate);
        ExerciseType testExerciseType = exerciseTypeList.get(exerciseTypeList.size() - 1);
        assertThat(testExerciseType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExerciseType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingExerciseType() throws Exception {
        int databaseSizeBeforeUpdate = exerciseTypeRepository.findAll().size();

        // Create the ExerciseType
        ExerciseTypeDTO exerciseTypeDTO = exerciseTypeMapper.toDto(exerciseType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExerciseTypeMockMvc.perform(put("/api/exercise-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exerciseTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExerciseType in the database
        List<ExerciseType> exerciseTypeList = exerciseTypeRepository.findAll();
        assertThat(exerciseTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExerciseType() throws Exception {
        // Initialize the database
        exerciseTypeRepository.saveAndFlush(exerciseType);

        int databaseSizeBeforeDelete = exerciseTypeRepository.findAll().size();

        // Delete the exerciseType
        restExerciseTypeMockMvc.perform(delete("/api/exercise-types/{id}", exerciseType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExerciseType> exerciseTypeList = exerciseTypeRepository.findAll();
        assertThat(exerciseTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
