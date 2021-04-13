package fr.perform.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.perform.web.rest.TestUtil;

public class WorkoutGoalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkoutGoal.class);
        WorkoutGoal workoutGoal1 = new WorkoutGoal();
        workoutGoal1.setId(1L);
        WorkoutGoal workoutGoal2 = new WorkoutGoal();
        workoutGoal2.setId(workoutGoal1.getId());
        assertThat(workoutGoal1).isEqualTo(workoutGoal2);
        workoutGoal2.setId(2L);
        assertThat(workoutGoal1).isNotEqualTo(workoutGoal2);
        workoutGoal1.setId(null);
        assertThat(workoutGoal1).isNotEqualTo(workoutGoal2);
    }
}
