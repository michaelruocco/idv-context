package uk.co.idv.context.adapter.json.context.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidMethodNameExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new InvalidMethodNameException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
