package uk.co.idv.context.adapter.json.policy.sequence.nextmethods;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidNextMethodPolicyTypeExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "message";

        Throwable error = new InvalidNextMethodPolicyTypeException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
