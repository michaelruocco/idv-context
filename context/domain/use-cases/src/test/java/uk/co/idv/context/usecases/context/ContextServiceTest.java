package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequestMother;

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
        DefaultCreateContextRequest request = DefaultCreateContextRequestMother.build();

        Context context = service.create(request);

        assertThat(context.getId()).isEqualTo(expected);
    }

    @Test
    void shouldPopulateCreatedOnContext() {
        Instant expected = Instant.now();
        given(clock.instant()).willReturn(expected);
        DefaultCreateContextRequest request = DefaultCreateContextRequestMother.build();

        Context context = service.create(request);

        assertThat(context.getCreated()).isEqualTo(expected);
    }

    @Test
    void shouldPopulateRequestOnContext() {
        DefaultCreateContextRequest request = DefaultCreateContextRequestMother.build();

        Context context = service.create(request);

        assertThat(context.getRequest()).isEqualTo(request);
    }

}
