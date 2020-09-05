package uk.co.idv.context.usecases.context.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MethodBuilderNotConfiguredExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new MethodBuilderNotConfiguredException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
