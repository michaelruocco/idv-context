package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotNextMethodInSequenceExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "method-name";

        Throwable error = new NotNextMethodInSequenceException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
