package uk.co.idv.method.entities.verification;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CompleteVerificationRequestTest {

    @Test
    void shouldReturnContextId() {
        UUID id = UUID.randomUUID();

        CompleteVerificationRequest request = CompleteVerificationRequest.builder()
                .id(id)
                .build();

        assertThat(request.getId()).isEqualTo(id);
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

        assertThat(request.getTimestamp()).contains(timestamp);
    }

    @Test
    void shouldReturnThrowExceptionIfForceGetTimestampWhenNotSet() {
        UUID id = UUID.randomUUID();
        CompleteVerificationRequest request = CompleteVerificationRequest.builder()
                .id(id)
                .build();

        Throwable error = catchThrowable(request::forceGetTimestamp);

        assertThat(error)
                .isInstanceOf(CompleteVerificationTimestampNotProvidedException.class)
                .hasMessage(id.toString());
    }

    @Test
    void shouldReturnUpdatedRequestWithOriginalTimestampIfAlreadySet() {
        Instant timestamp = Instant.parse("2021-01-01T16:41:20.172Z");
        CompleteVerificationRequest original = CompleteVerificationRequest.builder()
                .timestamp(timestamp)
                .build();
        Instant providedTimestamp = Instant.parse("2021-01-01T16:41:20.172Z");

        CompleteVerificationRequest updated = original.withTimestampIfNotProvided(providedTimestamp);

        assertThat(updated).isEqualTo(original);
    }

    @Test
    void shouldReturnUpdatedRequestWithProvidedTimestampIfNotAlreadySet() {
        CompleteVerificationRequest original = CompleteVerificationRequest.builder()
                .build();
        Instant providedTimestamp = Instant.parse("2021-01-01T16:41:20.172Z");

        CompleteVerificationRequest updated = original.withTimestampIfNotProvided(providedTimestamp);

        assertThat(updated.getTimestamp()).contains(providedTimestamp);
        assertThat(updated).usingRecursiveComparison()
                .ignoringFields("timestamp")
                .isEqualTo(original);
    }

}
