package uk.co.idv.context.adapter.dynamo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WaitForTableToBeActiveExceptionTest {

    @Test
    void shouldReturnCause() {
        Throwable cause = new Exception();

        Throwable error = new WaitForTableToBeActiveException(cause);

        assertThat(error.getCause()).isEqualTo(cause);
    }

}
