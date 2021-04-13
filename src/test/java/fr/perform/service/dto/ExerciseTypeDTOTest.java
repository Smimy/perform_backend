package fr.perform.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.perform.web.rest.TestUtil;

public class ExerciseTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExerciseTypeDTO.class);
        ExerciseTypeDTO exerciseTypeDTO1 = new ExerciseTypeDTO();
        exerciseTypeDTO1.setId(1L);
        ExerciseTypeDTO exerciseTypeDTO2 = new ExerciseTypeDTO();
        assertThat(exerciseTypeDTO1).isNotEqualTo(exerciseTypeDTO2);
        exerciseTypeDTO2.setId(exerciseTypeDTO1.getId());
        assertThat(exerciseTypeDTO1).isEqualTo(exerciseTypeDTO2);
        exerciseTypeDTO2.setId(2L);
        assertThat(exerciseTypeDTO1).isNotEqualTo(exerciseTypeDTO2);
        exerciseTypeDTO1.setId(null);
        assertThat(exerciseTypeDTO1).isNotEqualTo(exerciseTypeDTO2);
    }
}
