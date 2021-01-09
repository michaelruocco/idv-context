package uk.co.idv.context.adapter.client.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExtractRequestBodyExceptionTest {

    @Test
    void shouldReturnCause() {
        Throwable cause = new Exception("error");

        Throwable error = new ExtractRequestBodyException(cause);

        assertThat(error.getCause()).isEqualTo(cause);
    }

}
