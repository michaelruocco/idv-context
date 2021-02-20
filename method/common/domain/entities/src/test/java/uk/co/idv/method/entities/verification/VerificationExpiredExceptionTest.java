package uk.co.idv.method.entities.verification;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationExpiredExceptionTest {

    @Test
    void shouldReturnMessage() {
        UUID id = UUID.fromString("31360176-b29c-4279-9c58-250eecce8667");
        Instant expiry = Instant.parse("2021-01-01T19:02:36.440Z");

        Throwable error = new VerificationExpiredException(id, expiry);

        String expectedMessage = String.format("verification %s expired at %s", id.toString(), expiry.toString());
        assertThat(error.getMessage()).isEqualTo(expectedMessage);
    }

}
