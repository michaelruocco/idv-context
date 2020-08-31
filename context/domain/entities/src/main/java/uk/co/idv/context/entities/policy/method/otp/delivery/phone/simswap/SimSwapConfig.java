package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class SimSwapConfig {

    private final boolean async;
    private final AcceptableSimSwapResults acceptableResults;
    private final Duration timeout;
    private final Long minDaysSinceSwap;

    public boolean isAcceptable(String result) {
        return acceptableResults.isAcceptable(result);
    }

    public Optional<Duration> getTimeout() {
        return Optional.ofNullable(timeout);
    }

    public Optional<Long> getMinDaysSinceSwap() {
        return Optional.ofNullable(minDaysSinceSwap);
    }

    public Optional<Instant> calculateCutoff(Instant now) {
        return getMinDurationSinceSwap().map(now::minus);
    }

    private Optional<Duration> getMinDurationSinceSwap() {
        return getMinDaysSinceSwap().map(Duration::ofDays);
    }

}
