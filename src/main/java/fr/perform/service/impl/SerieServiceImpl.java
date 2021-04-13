package fr.perform.service.impl;

import fr.perform.service.SerieService;
import fr.perform.domain.Serie;
import fr.perform.repository.SerieRepository;
import fr.perform.service.dto.SerieDTO;
import fr.perform.service.mapper.SerieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Serie}.
 */
@Service
@Transactional
public class SerieServiceImpl implements SerieService {

    private final Logger log = LoggerFactory.getLogger(SerieServiceImpl.class);

    private final SerieRepository serieRepository;

    private final SerieMapper serieMapper;

    public SerieServiceImpl(SerieRepository serieRepository, SerieMapper serieMapper) {
        this.serieRepository = serieRepository;
        this.serieMapper = serieMapper;
    }

    @Override
    public SerieDTO save(SerieDTO serieDTO) {
        log.debug("Request to save Serie : {}", serieDTO);
        Serie serie = serieMapper.toEntity(serieDTO);
        serie = serieRepository.save(serie);
        return serieMapper.toDto(serie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SerieDTO> findAll() {
        log.debug("Request to get all Series");
        return serieRepository.findAll().stream()
            .map(serieMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SerieDTO> findOne(Long id) {
        log.debug("Request to get Serie : {}", id);
        return serieRepository.findById(id)
            .map(serieMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Serie : {}", id);
        serieRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SerieDTO> findAllByExerciseId(Long exerciseId) {
        log.debug("Request to get all Series by exerciseId {}", exerciseId);
        return serieMapper.toDto(serieRepository.findAllByExerciseId(exerciseId));
    }
}
