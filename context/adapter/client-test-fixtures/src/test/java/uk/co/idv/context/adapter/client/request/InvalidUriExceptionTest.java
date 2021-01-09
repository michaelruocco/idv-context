package uk.co.idv.context.adapter.client.request;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidUriExceptionTest {

    @Test
    void shouldReturnCause() {
        Throwable cause = new Exception("error");

        Throwable error = new InvalidUriException(cause);

        assertThat(error.getCause()).isEqualTo(cause);
    }

}
