package uk.co.idv.context.usecases.context.method.otp.simswap.async;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AsyncSimSwapContextNotFoundExceptionTest {

    @Test
    void shouldPopulateMessage() {
        UUID id = UUID.randomUUID();

        Throwable error = new AsyncSimSwapContextNotFoundException(id);

        assertThat(error.getMessage()).isEqualTo(id.toString());
    }

}
