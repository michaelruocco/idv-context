package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.IdentityCreateContextRequest;
import uk.co.idv.context.entities.context.create.IdentityCreateContextRequestMother;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextServiceTest {

    private final IdGenerator idGenerator = mock(IdGenerator.class);
    private final Clock clock = mock(Clock.class);

    private final ContextService service = ContextService.builder()
            .idGenerator(idGenerator)
            .clock(clock)
            .build();

    @Test
    void shouldPopulateIdOnContext() {
        UUID expected = UUID.randomUUID();
        given(idGenerator.generate()).willReturn(expected);
        IdentityCreateContextRequest request = IdentityCreateContextRequestMother.build();

        Context context = service.create(request);

        assertThat(context.getId()).isEqualTo(expected);
    }

    @Test
    void shouldPopulateCreatedOnContext() {
        Instant expected = Instant.now();
        given(clock.instant()).willReturn(expected);
        IdentityCreateContextRequest request = IdentityCreateContextRequestMother.build();

        Context context = service.create(request);

        assertThat(context.getCreated()).isEqualTo(expected);
    }

    @Test
    void shouldPopulateRequestOnContext() {
        IdentityCreateContextRequest request = IdentityCreateContextRequestMother.build();

        Context context = service.create(request);

        assertThat(context.getRequest()).isEqualTo(request);
    }

}
