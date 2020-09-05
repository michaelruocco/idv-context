package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SimSwapConfigTest {

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

        assertThat(config.getTimeout()).isEqualTo(timeout);
    }

    @Test
    void shouldReturnMinDaysSinceSwap() {
        long days = 5;

        SimSwapConfig config = SimSwapConfig.builder()
                .minDaysSinceSwap(days)
                .build();

        assertThat(config.getMinDaysSinceSwap()).isEqualTo(days);
    }

    @Test
    void shouldCalculateCutoff() {
        Instant now = Instant.parse("2020-08-31T12:00:00.000Z");
        long days = 5;
        SimSwapConfig config = SimSwapConfig.builder()
                .minDaysSinceSwap(days)
                .build();

        Instant cutoff = config.calculateCutoff(now);

        assertThat(cutoff).isEqualTo(now.minus(Duration.ofDays(days)));
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
