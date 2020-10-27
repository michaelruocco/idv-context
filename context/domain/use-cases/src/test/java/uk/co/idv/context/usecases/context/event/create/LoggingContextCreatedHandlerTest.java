package uk.co.idv.context.usecases.context.event.create;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.usecases.context.MdcPopulator;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoggingContextCreatedHandlerTest {

    private final MdcPopulator mdcPopulator = mock(MdcPopulator.class);

    private final LoggingContextCreatedHandler handler = new LoggingContextCreatedHandler(mdcPopulator);

    @Test
    void shouldPopulateContextIdOnMdc() {
        UUID id = UUID.randomUUID();
        Context context = givenContextWithId(id);

        handler.created(context);

        ArgumentCaptor<UUID> captor = ArgumentCaptor.forClass(UUID.class);
        verify(mdcPopulator).populateContextId(captor.capture());
        assertThat(captor.getValue()).isEqualTo(id);
    }

    private Context givenContextWithId(UUID id) {
        Context context = mock(Context.class);
        given(context.getId()).willReturn(id);
        return context;
    }

}
