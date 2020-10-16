package uk.co.idv.policy.adapter.json.key;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidPolicyKeyTypeExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new InvalidPolicyKeyTypeException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
