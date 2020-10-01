package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ContextNotFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        UUID id = UUID.fromString("886dc05e-776b-41e1-83cb-d4720d57f020");

        Throwable error = new ContextNotFoundException(id);

        assertThat(error.getMessage()).isEqualTo(id.toString());
    }

}
