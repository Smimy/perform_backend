package fr.perform.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.perform.web.rest.TestUtil;

public class WorkoutGoalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkoutGoalDTO.class);
        WorkoutGoalDTO workoutGoalDTO1 = new WorkoutGoalDTO();
        workoutGoalDTO1.setId(1L);
        WorkoutGoalDTO workoutGoalDTO2 = new WorkoutGoalDTO();
        assertThat(workoutGoalDTO1).isNotEqualTo(workoutGoalDTO2);
        workoutGoalDTO2.setId(workoutGoalDTO1.getId());
        assertThat(workoutGoalDTO1).isEqualTo(workoutGoalDTO2);
        workoutGoalDTO2.setId(2L);
        assertThat(workoutGoalDTO1).isNotEqualTo(workoutGoalDTO2);
        workoutGoalDTO1.setId(null);
        assertThat(workoutGoalDTO1).isNotEqualTo(workoutGoalDTO2);
    }
}
