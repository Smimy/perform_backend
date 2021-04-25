package fr.perform.service.impl;

import fr.perform.service.ExerciseService;
import fr.perform.domain.Exercise;
import fr.perform.repository.ExerciseRepository;
import fr.perform.service.ExerciseTypeService;
import fr.perform.service.SerieService;
import fr.perform.service.dto.ExerciseDTO;
import fr.perform.service.dto.ExerciseTypeDTO;
import fr.perform.service.dto.SerieDTO;
import fr.perform.service.mapper.ExerciseMapper;
import fr.perform.utils.wrappers.WrapperExercise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Exercise}.
 */
@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private final Logger log = LoggerFactory.getLogger(ExerciseServiceImpl.class);

    private final ExerciseRepository exerciseRepository;

    private final ExerciseMapper exerciseMapper;

    private final ExerciseTypeService exerciseTypeService;
    private final SerieService serieService;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository,
                               ExerciseMapper exerciseMapper,
                               ExerciseTypeService exerciseTypeService,
                               SerieService serieService) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
        this.exerciseTypeService = exerciseTypeService;
        this.serieService = serieService;
    }

    @Override
    public ExerciseDTO save(ExerciseDTO exerciseDTO) {
        log.debug("Request to save Exercise : {}", exerciseDTO);
        Exercise exercise = exerciseMapper.toEntity(exerciseDTO);
        exercise = exerciseRepository.save(exercise);
        return exerciseMapper.toDto(exercise);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseDTO> findAll() {
        log.debug("Request to get all Exercises");
        return exerciseRepository.findAll().stream()
            .map(exerciseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ExerciseDTO> findOne(Long id) {
        log.debug("Request to get Exercise : {}", id);
        return exerciseRepository.findById(id)
            .map(exerciseMapper::toDto);
    }

    /**
     * @author Jérémy Schrotzenberger.
     *
     * @param id the id of the exercise.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exercise : {}", id);
        final Optional<WrapperExercise> wrapperExercise = findWrapperExerciseById(id);
        if (wrapperExercise.isPresent()) {
            for (SerieDTO serieDTO : wrapperExercise.get().getSerieDTOList()) {
                serieService.delete(serieDTO.getId());
            }
        }
        exerciseRepository.deleteById(id);
    }

    /**
     * @author Jérémy Schrotzenberger.
     *
     * @param id the id of the exercise.
     * @return wrapperExercise which contains all series linked to the id of the exercise, if they exist;
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WrapperExercise> findWrapperExerciseById(final Long id) {
        log.debug("Request to get WrapperExercise : {}", id);
        final ExerciseDTO exerciseDTO = findOne(id).get();
        final ExerciseTypeDTO exerciseTypeDTO = exerciseTypeService.findOne(exerciseDTO.getExerciseTypeId()).get();
        final List<SerieDTO> serieDTOList = serieService.findAllByExerciseId(id);

        return Optional.of(new WrapperExercise(exerciseDTO, exerciseTypeDTO, serieDTOList));
    }

    /**
     * @author Jérémy Schrotzenberger.
     *
     * @param workoutId the id of the workout.
     * @return wrapperExerciseList which contains all exercises and series linked to the id of the workout, if they exist.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WrapperExercise> findAllWrapperExerciseByWorkoutId(Long workoutId) {
        log.debug("Request to get all WrapperExercise : {}", workoutId);
        final List<WrapperExercise> wrapperExerciseList = new ArrayList<>();
        final List<ExerciseDTO> exerciseDTOList = exerciseMapper.toDto(exerciseRepository.findAllExercisesByWorkoutId(workoutId));
        for(ExerciseDTO exerciseDTO : exerciseDTOList) {
            WrapperExercise wrapperExercise = findWrapperExerciseById(exerciseDTO.getId()).get();
            wrapperExerciseList.add(wrapperExercise);
        }
        return wrapperExerciseList;
    }
}
