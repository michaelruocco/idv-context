package uk.co.idv.context.usecases.common;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RandomIdGeneratorTest {

    private final IdGenerator generator = new RandomIdGenerator();

    @Test
    void shouldReturnRandomId() {
        UUID id1 = generator.generate();
        UUID id2 = generator.generate();

        assertThat(id1).isNotEqualTo(id2);
    }

}
