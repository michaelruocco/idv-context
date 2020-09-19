package uk.co.idv.context.adapter.json.policy.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidMethodPolicyNameExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new InvalidMethodPolicyNameException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
