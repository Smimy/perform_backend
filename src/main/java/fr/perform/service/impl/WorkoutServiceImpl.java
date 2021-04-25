package fr.perform.service.impl;

import fr.perform.service.ExerciseService;
import fr.perform.service.SerieService;
import fr.perform.service.WorkoutGoalService;
import fr.perform.service.WorkoutService;
import fr.perform.domain.Workout;
import fr.perform.repository.WorkoutRepository;
import fr.perform.service.dto.ExerciseDTO;
import fr.perform.service.dto.SerieDTO;
import fr.perform.service.dto.WorkoutDTO;
import fr.perform.service.dto.WorkoutGoalDTO;
import fr.perform.service.mapper.WorkoutMapper;
import fr.perform.utils.wrappers.WrapperExercise;
import fr.perform.utils.wrappers.WrapperWorkout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Workout}.
 */
@Service
@Transactional
public class WorkoutServiceImpl implements WorkoutService {

    private final Logger log = LoggerFactory.getLogger(WorkoutServiceImpl.class);

    private final WorkoutRepository workoutRepository;

    private final WorkoutMapper workoutMapper;

    private final WorkoutGoalService workoutGoalService;
    private final ExerciseService exerciseService;
    private final SerieService serieService;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository,
                              WorkoutMapper workoutMapper,
                              WorkoutGoalService workoutGoalService,
                              ExerciseService exerciseService,
                              SerieService serieService) {
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
        this.workoutGoalService = workoutGoalService;
        this.exerciseService = exerciseService;
        this.serieService = serieService;
    }

    @Override
    public WorkoutDTO save(WorkoutDTO workoutDTO) {
        log.debug("Request to save Workout : {}", workoutDTO);
        Workout workout = workoutMapper.toEntity(workoutDTO);
        workout = workoutRepository.save(workout);
        return workoutMapper.toDto(workout);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutDTO> findAll() {
        log.debug("Request to get all Workouts");
        return workoutRepository.findAll().stream()
            .map(workoutMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutDTO> findByUserId(Long userId) {
        log.debug("Request to get all Workouts for a user : {}", userId);
        return workoutRepository.findByUserId(userId).stream()
            .map(workoutMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkoutDTO> findOne(Long id) {
        log.debug("Request to get Workout : {}", id);
        return workoutRepository.findById(id)
            .map(workoutMapper::toDto);
    }

    /**
     * @author Jérémy Schrotzenberger.
     *
     * @param id the id of the Workout.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Workout : {}", id);
        final List<WrapperExercise> wrapperExerciseList = exerciseService.findAllWrapperExerciseByWorkoutId(id);
        if (!wrapperExerciseList.isEmpty()) {
            for (WrapperExercise wrapperExercise : wrapperExerciseList) {
                exerciseService.delete(wrapperExercise.getId());
            }
        }
        workoutRepository.deleteById(id);
    }

    /**
     * @author Jérémy Schrotzenberger.
     *
     * @param id the id of the Workout.
     * @return wrapperWorkout which contains all exercises and series linked to it, if it exists.
     */
    @Override
    @Transactional (readOnly = true)
    public Optional<WrapperWorkout> findById(Long id) {
        log.debug("Request to find WrapperWorkout : {}", id);
        final WorkoutDTO workoutDTO = findOne(id).get();
        final WorkoutGoalDTO workoutGoalDTO = workoutGoalService.findOne(workoutDTO.getWorkoutGoalId()).get();
        final List<WrapperExercise> wrapperExerciseList = exerciseService.findAllWrapperExerciseByWorkoutId(id);
        final Optional<WrapperWorkout> wrapperWorkout = Optional.of(new WrapperWorkout(workoutDTO, workoutGoalDTO, wrapperExerciseList));
    return wrapperWorkout.isPresent() ? Optional.of(wrapperWorkout.get()) : Optional.empty();
    }
}
