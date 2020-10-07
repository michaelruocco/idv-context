package uk.co.idv.context.usecases.context.result;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CannotAddResultForMethodExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "error-message";

        Throwable error = new CannotAddResultForMethodException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
