package uk.co.idv.context.entities.context.result;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ResultTest {

    @Test
    void shouldReturnMethodName() {
        String methodName = "method-name";

        Result result = Result.builder()
                .methodName(methodName)
                .build();

        assertThat(result.getMethodName()).isEqualTo(methodName);
    }

    @Test
    void shouldReturnSuccessful() {
        Result result = Result.builder()
                .successful(true)
                .build();

        assertThat(result.isSuccessful()).isTrue();
    }

    @Test
    void shouldReturnVerificationId() {
        UUID id = UUID.randomUUID();

        Result result = Result.builder()
                .verificationId(id)
                .build();

        assertThat(result.getVerificationId()).isEqualTo(id);
    }

    @Test
    void shouldReturnTimestamp() {
        Instant timestamp = Instant.now();

        Result result = Result.builder()
                .timestamp(timestamp)
                .build();

        assertThat(result.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnTrueIfIsForMethodName() {
        String methodName = "method-name";
        Result result = Result.builder()
                .methodName(methodName)
                .build();

        boolean forMethod = result.isFor(methodName);

        assertThat(forMethod).isTrue();
    }

    @Test
    void shouldReturnFalseIfIsNotForMethodName() {
        String methodName = "method-name";
        Result result = Result.builder()
                .methodName(methodName)
                .build();

        boolean forMethod = result.isFor("other-method-name");

        assertThat(forMethod).isFalse();
    }

}
