package uk.co.idv.method.entities.eligibility;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultAsyncEligibilityTest {

    @Test
    void shouldReturnEligible() {
        DefaultAsyncEligibility eligibility = DefaultAsyncEligibility.builder()
                .eligible(true)
                .build();

        assertThat(eligibility.isEligible()).isTrue();
    }

    @Test
    void shouldReturnComplete() {
        DefaultAsyncEligibility eligibility = DefaultAsyncEligibility.builder()
                .complete(true)
                .build();

        assertThat(eligibility.isComplete()).isTrue();
    }

    @Test
    void shouldReturnEmptyReasonIfNotSet() {
        DefaultAsyncEligibility eligibility = DefaultAsyncEligibility.builder()
                .build();

        assertThat(eligibility.getReason()).isEmpty();
    }

    @Test
    void shouldReturnReason() {
        String reason = "my reason";

        DefaultAsyncEligibility eligibility = DefaultAsyncEligibility.builder()
                .reason(reason)
                .build();

        assertThat(eligibility.getReason()).contains(reason);
    }

}
