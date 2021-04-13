package fr.perform.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.perform.web.rest.TestUtil;

public class SerieDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SerieDTO.class);
        SerieDTO serieDTO1 = new SerieDTO();
        serieDTO1.setId(1L);
        SerieDTO serieDTO2 = new SerieDTO();
        assertThat(serieDTO1).isNotEqualTo(serieDTO2);
        serieDTO2.setId(serieDTO1.getId());
        assertThat(serieDTO1).isEqualTo(serieDTO2);
        serieDTO2.setId(2L);
        assertThat(serieDTO1).isNotEqualTo(serieDTO2);
        serieDTO1.setId(null);
        assertThat(serieDTO1).isNotEqualTo(serieDTO2);
    }
}
