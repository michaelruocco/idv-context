package uk.co.idv.method.adapter.otp.protect.mask;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MethodNotOtpExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "message";

        Throwable error = new MethodNotOtpException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
