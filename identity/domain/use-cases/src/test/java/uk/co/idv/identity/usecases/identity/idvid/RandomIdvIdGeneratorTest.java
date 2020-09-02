package uk.co.idv.identity.usecases.identity.idvid;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;

import static org.assertj.core.api.Assertions.assertThat;

class RandomIdvIdGeneratorTest {

    private final RandomIdvIdGenerator generator = new RandomIdvIdGenerator();

    @Test
    void shouldGenerateRandomIdvId() {
        IdvId id1 = generator.generate();
        IdvId id2 = generator.generate();

        assertThat(id1).isNotEqualTo(id2);
    }

}
