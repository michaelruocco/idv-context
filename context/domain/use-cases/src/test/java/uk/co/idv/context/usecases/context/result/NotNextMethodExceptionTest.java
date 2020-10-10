package uk.co.idv.context.usecases.context.result;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotNextMethodExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "method-name";

        Throwable error = new NotNextMethodException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
