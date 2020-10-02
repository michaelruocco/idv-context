package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MethodNotEligibleExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "method-name";

        Throwable error = new MethodAlreadyCompleteException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
