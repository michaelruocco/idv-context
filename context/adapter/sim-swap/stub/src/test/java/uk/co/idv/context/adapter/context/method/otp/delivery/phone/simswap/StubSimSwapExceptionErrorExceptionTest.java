package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StubSimSwapExceptionErrorExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-number";

        Throwable error = new StubSimSwapExceptionErrorException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
