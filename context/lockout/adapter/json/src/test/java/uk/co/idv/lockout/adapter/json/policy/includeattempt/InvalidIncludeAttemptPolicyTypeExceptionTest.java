package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidIncludeAttemptPolicyTypeExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new InvalidIncludeAttemptPolicyTypeException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
