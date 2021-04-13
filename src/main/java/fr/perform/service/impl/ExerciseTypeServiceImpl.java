package fr.perform.service.impl;

import fr.perform.service.ExerciseTypeService;
import fr.perform.domain.ExerciseType;
import fr.perform.repository.ExerciseTypeRepository;
import fr.perform.service.dto.ExerciseTypeDTO;
import fr.perform.service.mapper.ExerciseTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ExerciseType}.
 */
@Service
@Transactional
public class ExerciseTypeServiceImpl implements ExerciseTypeService {

    private final Logger log = LoggerFactory.getLogger(ExerciseTypeServiceImpl.class);

    private final ExerciseTypeRepository exerciseTypeRepository;

    private final ExerciseTypeMapper exerciseTypeMapper;

    public ExerciseTypeServiceImpl(ExerciseTypeRepository exerciseTypeRepository, ExerciseTypeMapper exerciseTypeMapper) {
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.exerciseTypeMapper = exerciseTypeMapper;
    }

    @Override
    public ExerciseTypeDTO save(ExerciseTypeDTO exerciseTypeDTO) {
        log.debug("Request to save ExerciseType : {}", exerciseTypeDTO);
        ExerciseType exerciseType = exerciseTypeMapper.toEntity(exerciseTypeDTO);
        exerciseType = exerciseTypeRepository.save(exerciseType);
        return exerciseTypeMapper.toDto(exerciseType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseTypeDTO> findAll() {
        log.debug("Request to get all ExerciseTypes");
        return exerciseTypeRepository.findAll().stream()
            .map(exerciseTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ExerciseTypeDTO> findOne(Long id) {
        log.debug("Request to get ExerciseType : {}", id);
        return exerciseTypeRepository.findById(id)
            .map(exerciseTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExerciseType : {}", id);
        exerciseTypeRepository.deleteById(id);
    }
}
