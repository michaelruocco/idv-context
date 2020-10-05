package uk.co.idv.method.entities.otp.policy.delivery.phone;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SimSwapConfigTest {

    @Test
    void shouldReturnAcceptableResults() {
        AcceptableSimSwapStatuses acceptableResults = mock(AcceptableSimSwapStatuses.class);

        SimSwapConfig config = SimSwapConfig.builder()
                .acceptableStatuses(acceptableResults)
                .build();

        assertThat(config.getAcceptableStatuses()).isEqualTo(acceptableResults);
    }

    @Test
    void shouldReturnTimeout() {
        Duration timeout = Duration.ofSeconds(30);

        SimSwapConfig config = SimSwapConfig.builder()
                .timeout(timeout)
                .build();

        assertThat(config.getTimeout()).isEqualTo(timeout);
    }

    @Test
    void shouldReturnMinDaysSinceSwapIfConfigured() {
        long days = 5;

        SimSwapConfig config = SimSwapConfig.builder()
                .minDaysSinceSwap(days)
                .build();

        assertThat(config.getMinDaysSinceSwap()).contains(days);
    }

    @Test
    void shouldReturnEmptyIfMinDaysSinceSwapNotConfigured() {
        SimSwapConfig config = SimSwapConfig.builder()
                .build();

        assertThat(config.getMinDaysSinceSwap()).isEmpty();
    }

    @Test
    void shouldCalculateCutoffIfMinDaysSinceSimSwapConfigured() {
        Instant now = Instant.parse("2020-08-31T12:00:00.000Z");
        long days = 5;
        SimSwapConfig config = SimSwapConfig.builder()
                .minDaysSinceSwap(days)
                .build();

        Optional<Instant> cutoff = config.calculateCutoff(now);

        assertThat(cutoff).contains(now.minus(Duration.ofDays(days)));
    }

    @Test
    void shouldReturnEmptyCutoffIfMinDaysSinceSimSwapNotConfigured() {
        Instant now = Instant.parse("2020-08-31T12:00:00.000Z");
        SimSwapConfig config = SimSwapConfig.builder()
                .build();

        Optional<Instant> cutoff = config.calculateCutoff(now);

        assertThat(cutoff).isEmpty();
    }

    @Test
    void shouldReturnResultAcceptable() {
        String result = "my-result";
        AcceptableSimSwapStatuses acceptableResults = mock(AcceptableSimSwapStatuses.class);
        given(acceptableResults.isAcceptable(result)).willReturn(true);
        SimSwapConfig config = SimSwapConfig.builder()
                .acceptableStatuses(acceptableResults)
                .build();

        boolean acceptable = config.isAcceptable(result);

        assertThat(acceptable).isTrue();
    }

}
