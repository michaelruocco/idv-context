package uk.co.idv.method.usecases.otp.delivery;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodConfigNotSupportedExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-message";

        Throwable exception = new DeliveryMethodConfigNotSupportedException(message);

        assertThat(exception.getMessage()).isEqualTo(message);
    }

}
