package uk.co.idv.config.repository.dynamo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DynamoExceptionTest {

    @Test
    void shouldReturnCause() {
        Throwable cause = new Exception();

        Throwable error = new DynamoException(cause);

        assertThat(error.getCause()).isEqualTo(cause);
    }

}
