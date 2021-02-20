package uk.co.idv.context.adapter.verification.client.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ClientExceptionTest {

    @Test
    void shouldReturnCause() {
        Exception cause =  mock(Exception.class);

        Throwable exception = new ClientException(cause);

        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    void shouldReturnMessage() {
        String expectedMessage = "error-message";

        Throwable exception = new ClientException(expectedMessage);

        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

}
