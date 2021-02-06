package uk.co.idv.context.entities.context;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ContextExpiredExceptionTest {

    @Test
    void shouldReturnMessage() {
        UUID id = UUID.fromString("2b2bf9a2-750e-4b32-9a56-7e50406e216e");
        Instant expiry = Instant.parse("2020-09-30T21:37:17.027Z");

        Throwable error = new ContextExpiredException(id, expiry);

        String expectedMessage = String.format("context %s expired at %s", id, expiry);
        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

}
