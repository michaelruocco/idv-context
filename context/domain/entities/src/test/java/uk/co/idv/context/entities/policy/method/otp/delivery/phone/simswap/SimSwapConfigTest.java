package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SimSwapConfigTest {

    @Test
    void shouldReturnAsync() {
        SimSwapConfig config = SimSwapConfig.builder()
                .async(true)
                .build();

        assertThat(config.isAsync()).isTrue();
    }

    @Test
    void shouldReturnAcceptableResults() {
        AcceptableSimSwapResults acceptableResults = mock(AcceptableSimSwapResults.class);

        SimSwapConfig config = SimSwapConfig.builder()
                .acceptableResults(acceptableResults)
                .build();

        assertThat(config.getAcceptableResults()).isEqualTo(acceptableResults);
    }

    @Test
    void shouldReturnTimeout() {
        Duration timeout = Duration.ofSeconds(30);

        SimSwapConfig config = SimSwapConfig.builder()
                .timeout(timeout)
                .build();

        assertThat(config.getTimeout()).contains(timeout);
    }

    @Test
    void shouldReturnEmptyTimeoutIfNotConfigured() {
        SimSwapConfig config = SimSwapConfig.builder()
                .build();

        assertThat(config.getTimeout()).isEmpty();
    }

    @Test
    void shouldReturnMinDaysSinceSwap() {
        long days = 5;

        SimSwapConfig config = SimSwapConfig.builder()
                .minDaysSinceSwap(days)
                .build();

        assertThat(config.getMinDaysSinceSwap()).contains(days);
    }

    @Test
    void shouldReturnEmptyMinDaysSinceSwapIfNotConfigured() {
        SimSwapConfig config = SimSwapConfig.builder()
                .build();

        assertThat(config.getMinDaysSinceSwap()).isEmpty();
    }

    @Test
    void shouldCalculateCutoff() {
        Instant now = Instant.parse("2020-08-31T12:00:00.000Z");
        long days = 5;
        SimSwapConfig config = SimSwapConfig.builder()
                .minDaysSinceSwap(days)
                .build();

        Optional<Instant> cutoff = config.calculateCutoff(now);

        assertThat(cutoff).contains(now.minus(Duration.ofDays(days)));
    }

    @Test
    void shouldReturnResultAcceptable() {
        String result = "my-result";
        AcceptableSimSwapResults acceptableResults = mock(AcceptableSimSwapResults.class);
        given(acceptableResults.isAcceptable(result)).willReturn(true);
        SimSwapConfig config = SimSwapConfig.builder()
                .acceptableResults(acceptableResults)
                .build();

        boolean acceptable = config.isAcceptable(result);

        assertThat(acceptable).isTrue();
    }

}
