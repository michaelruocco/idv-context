package uk.co.idv.context.adapter.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StubSimSwapErrorExceptionTest {

    @Test
    void shouldReturnMessage() {
        String message = "my-number";

        Throwable error = new StubSimSwapErrorException(message);

        assertThat(error.getMessage()).isEqualTo(message);
    }

}
