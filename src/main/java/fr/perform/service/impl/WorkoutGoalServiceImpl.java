package fr.perform.service.impl;

import fr.perform.service.WorkoutGoalService;
import fr.perform.domain.WorkoutGoal;
import fr.perform.repository.WorkoutGoalRepository;
import fr.perform.service.dto.WorkoutGoalDTO;
import fr.perform.service.mapper.WorkoutGoalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link WorkoutGoal}.
 */
@Service
@Transactional
public class WorkoutGoalServiceImpl implements WorkoutGoalService {

    private final Logger log = LoggerFactory.getLogger(WorkoutGoalServiceImpl.class);

    private final WorkoutGoalRepository workoutGoalRepository;

    private final WorkoutGoalMapper workoutGoalMapper;

    public WorkoutGoalServiceImpl(WorkoutGoalRepository workoutGoalRepository, WorkoutGoalMapper workoutGoalMapper) {
        this.workoutGoalRepository = workoutGoalRepository;
        this.workoutGoalMapper = workoutGoalMapper;
    }

    @Override
    public WorkoutGoalDTO save(WorkoutGoalDTO workoutGoalDTO) {
        log.debug("Request to save WorkoutGoal : {}", workoutGoalDTO);
        WorkoutGoal workoutGoal = workoutGoalMapper.toEntity(workoutGoalDTO);
        workoutGoal = workoutGoalRepository.save(workoutGoal);
        return workoutGoalMapper.toDto(workoutGoal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutGoalDTO> findAll() {
        log.debug("Request to get all WorkoutGoals");
        return workoutGoalRepository.findAll().stream()
            .map(workoutGoalMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<WorkoutGoalDTO> findOne(Long id) {
        log.debug("Request to get WorkoutGoal : {}", id);
        return workoutGoalRepository.findById(id)
            .map(workoutGoalMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkoutGoal : {}", id);
        workoutGoalRepository.deleteById(id);
    }
}
