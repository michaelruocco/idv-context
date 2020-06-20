package uk.co.idv.context.adapter.eligibility.external.data.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoDigitsFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new NoDigitsFoundException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
