package uk.co.idv.context.entities.verification;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CompleteVerificationRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID id = UUID.randomUUID();

        CompleteVerificationRequest request = CompleteVerificationRequest.builder()
                .verificationId(id)
                .build();

        assertThat(request.getVerificationId()).isEqualTo(id);
    }

    @Test
    void shouldReturnSuccessful() {
        CompleteVerificationRequest request = CompleteVerificationRequest.builder()
                .successful(true)
                .build();

        assertThat(request.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnTimestamp() {
        Instant timestamp = Instant.now();

        CompleteVerificationRequest request = CompleteVerificationRequest.builder()
                .timestamp(timestamp)
                .build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
    }

}
