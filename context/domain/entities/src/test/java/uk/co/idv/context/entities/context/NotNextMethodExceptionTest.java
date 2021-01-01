package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotNextMethodExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "error-message";

        Throwable error = new NotNextMethodException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
