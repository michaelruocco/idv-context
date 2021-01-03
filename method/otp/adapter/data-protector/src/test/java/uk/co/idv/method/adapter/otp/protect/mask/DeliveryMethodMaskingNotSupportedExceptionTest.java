package uk.co.idv.method.adapter.otp.protect.mask;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeliveryMethodMaskingNotSupportedExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "error-message";

        Throwable error = new DeliveryMethodMaskingNotSupportedException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
