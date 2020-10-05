package uk.co.idv.method.entities.otp.delivery.phone;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidPhoneNumberValueExceptionTest {

    @Test
    void shouldReturnCause() {
        Throwable cause = new Exception();

        Throwable exception = new InvalidPhoneNumberValueException("", cause);

        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    void shouldReturnMessage() {
        String value = "my-value";

        Throwable exception = new InvalidPhoneNumberValueException(value, new Exception());

        assertThat(exception.getMessage()).isEqualTo(value);
    }

}
