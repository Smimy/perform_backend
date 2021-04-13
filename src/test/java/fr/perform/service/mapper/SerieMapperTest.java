package fr.perform.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SerieMapperTest {

    private SerieMapper serieMapper;

    @BeforeEach
    public void setUp() {
        serieMapper = new SerieMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(serieMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(serieMapper.fromId(null)).isNull();
    }
}
