package uk.co.idv.lockout.adapter.json.policy.state;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidLockoutStateCalculatorTypeExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new InvalidLockoutStateCalculatorTypeException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
