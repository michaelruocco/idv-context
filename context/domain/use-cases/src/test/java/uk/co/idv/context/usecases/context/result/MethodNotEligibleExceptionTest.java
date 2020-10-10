package uk.co.idv.context.usecases.context.result;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MethodNotEligibleExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "method-name";

        Throwable error = new MethodNotEligibleException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
