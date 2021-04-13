package fr.perform.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExerciseTypeMapperTest {

    private ExerciseTypeMapper exerciseTypeMapper;

    @BeforeEach
    public void setUp() {
        exerciseTypeMapper = new ExerciseTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(exerciseTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(exerciseTypeMapper.fromId(null)).isNull();
    }
}
