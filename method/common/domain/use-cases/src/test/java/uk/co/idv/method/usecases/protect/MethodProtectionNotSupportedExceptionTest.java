package uk.co.idv.method.usecases.protect;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MethodProtectionNotSupportedExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "message";

        Throwable error = new MethodProtectionNotSupportedException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
