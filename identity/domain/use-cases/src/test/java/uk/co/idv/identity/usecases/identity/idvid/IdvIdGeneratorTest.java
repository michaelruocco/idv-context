package uk.co.idv.identity.usecases.identity.idvid;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.identity.entities.alias.IdvId;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdvIdGeneratorTest {

    private final IdGenerator idGenerator = mock(IdGenerator.class);

    private final IdvIdGenerator generator = new IdvIdGenerator(idGenerator);

    @Test
    void shouldGenerateIdvIdWithUuidId() {
        UUID id = UUID.randomUUID();
        given(idGenerator.generate()).willReturn(id);

        IdvId idvId = generator.generate();

        assertThat(idvId.getValueAsUuid()).isEqualTo(id);
    }

}
