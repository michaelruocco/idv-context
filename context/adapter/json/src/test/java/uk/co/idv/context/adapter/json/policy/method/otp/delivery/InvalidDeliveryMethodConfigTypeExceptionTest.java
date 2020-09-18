package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidDeliveryMethodConfigTypeExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable error = new InvalidDeliveryMethodConfigTypeException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
