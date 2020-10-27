package uk.co.idv.context.usecases.context.event.expiry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import uk.co.idv.context.entities.context.Context;

import java.util.HashMap;
import java.util.UUID;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExpiryHandlerTest {

    @BeforeEach
    void setUp() {
        MDC.setContextMap(new HashMap<>());
    }

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void shouldLogContextId() throws Exception {
        UUID id = UUID.randomUUID();
        Context context = givenContextWithId(id);
        Runnable handler = new ExpiryHandler(context);

        String output = tapSystemOut(handler::run);

        assertThat(output).contains(String.format("context %s expired", id));
    }

    private Context givenContextWithId(UUID id) {
        Context context = mock(Context.class);
        given(context.getId()).willReturn(id);
        return context;
    }

}
