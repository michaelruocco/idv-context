package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.eligibility.SimSwapStatusNotAllowed;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.eligibility.SimSwappedAfterCutoff;
import uk.co.idv.method.entities.eligibility.Eligibility;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SimSwapResultTest {

    private final SimSwapConfig config = mock(SimSwapConfig.class);

    @Test
    void shouldReturnSimSwapConfig() {
        SimSwapResult result = SimSwapResult.builder()
                .config(config)
                .build();

        assertThat(result.getConfig()).isEqualTo(config);
    }

    @Test
    void shouldReturnStatus() {
        String status = "my-status";

        SimSwapResult result = SimSwapResult.builder()
                .status(status)
                .build();

        assertThat(result.getStatus()).isEqualTo(status);
    }

    @Test
    void shouldReturnEligibleIfStatusIsAcceptableAndSimSwapCutoffNotConfiguredAndLastSwappedNotProvided() {
        Instant now = Instant.now();
        String status = "my-status";
        given(config.isAcceptable(status)).willReturn(true);
        SimSwapResult result = SimSwapResult.builder()
                .config(config)
                .status(status)
                .build();

        Eligibility eligibility = result.toEligibility(now);

        assertThat(eligibility.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfStatusIsNotAcceptableAndSimSwapCutoffNotConfiguredAndLastSwappedNotProvided() {
        Instant now = Instant.now();
        String status = "my-status";
        given(config.isAcceptable(status)).willReturn(false);
        SimSwapResult result = SimSwapResult.builder()
                .config(config)
                .status(status)
                .build();

        Eligibility eligibility = result.toEligibility(now);

        String expectedReason = String.format("sim swap status %s is not allowed", status);
        assertThat(eligibility).isInstanceOf(SimSwapStatusNotAllowed.class);
        assertThat(eligibility.getReason()).contains(expectedReason);
    }

    @Test
    void shouldReturnEligibleIfStatusIsAcceptableAndSimSwapCutoffNotConfiguredAndLastSwappedProvided() {
        Instant now = Instant.now();
        String status = "my-status";
        given(config.isAcceptable(status)).willReturn(true);
        SimSwapResult result = SimSwapResult.builder()
                .config(config)
                .lastSwapped(now)
                .status(status)
                .build();

        Eligibility eligibility = result.toEligibility(now);

        assertThat(eligibility.isEligible()).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfSimSwapCutoffAndLastSwappedAfterCutoff() {
        Instant now = Instant.parse("2020-09-13T20:37:53.240Z");
        Instant cutoff = Instant.now().minus(Duration.ofDays(5));
        Instant lastSwapped = cutoff.plusMillis(1);
        String status = "my-status";
        given(config.isAcceptable(status)).willReturn(true);
        given(config.calculateCutoff(now)).willReturn(Optional.of(cutoff));
        SimSwapResult result = SimSwapResult.builder()
                .config(config)
                .lastSwapped(lastSwapped)
                .status(status)
                .build();

        Eligibility eligibility = result.toEligibility(now);

        String expectedReason = String.format("sim swapped at %s (cutoff %s)", lastSwapped, cutoff);
        assertThat(eligibility).isInstanceOf(SimSwappedAfterCutoff.class);
        assertThat(eligibility.getReason()).contains(expectedReason);
    }

    @Test
    void shouldReturnEligibleIfSimSwapCutoffAndLastSwappedBeforeCutoff() {
        Instant now = Instant.parse("2020-09-13T20:37:53.240Z");
        Instant cutoff = Instant.now().minus(Duration.ofDays(5));
        Instant lastSwapped = cutoff.minusMillis(1);
        String status = "my-status";
        given(config.isAcceptable(status)).willReturn(true);
        given(config.calculateCutoff(now)).willReturn(Optional.of(cutoff));
        SimSwapResult result = SimSwapResult.builder()
                .config(config)
                .lastSwapped(lastSwapped)
                .status(status)
                .build();

        Eligibility eligibility = result.toEligibility(now);

        assertThat(eligibility.isEligible()).isTrue();
    }


}
