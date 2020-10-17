package uk.co.idv.context.usecases.context.event.expiry;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;

import java.util.UUID;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExpiryHandlerTest {

    @Test
    void shouldLogContextId() throws Exception {
        UUID id = UUID.randomUUID();
        Context context = givenContextWithId(id);
        Runnable handler = new ExpiryHandler(context);

        String output = tapSystemErr(handler::run);

        assertThat(output).contains(String.format("context %s expired", id));
    }

    private Context givenContextWithId(UUID id) {
        Context context = mock(Context.class);
        given(context.getId()).willReturn(id);
        return context;
    }

}
