package uk.co.idv.method.usecases.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimSwapExceptionTest {

    @Test
    void shouldReturnCause() {
        Throwable cause = new Exception();

        Throwable error = new SimSwapException(cause);

        assertThat(error.getCause()).isEqualTo(cause);
    }

}
