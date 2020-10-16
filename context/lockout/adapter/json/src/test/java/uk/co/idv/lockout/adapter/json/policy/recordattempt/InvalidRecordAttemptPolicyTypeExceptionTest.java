package uk.co.idv.lockout.adapter.json.policy.recordattempt;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidRecordAttemptPolicyTypeExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new InvalidRecordAttemptPolicyTypeException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
