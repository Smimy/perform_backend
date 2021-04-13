package fr.perform.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkoutGoalMapperTest {

    private WorkoutGoalMapper workoutGoalMapper;

    @BeforeEach
    public void setUp() {
        workoutGoalMapper = new WorkoutGoalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(workoutGoalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(workoutGoalMapper.fromId(null)).isNull();
    }
}
