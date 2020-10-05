package uk.co.idv.method.entities.otp.policy.delivery.phone;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class SimSwapConfig {

    private final AcceptableSimSwapStatuses acceptableStatuses;
    private final Duration timeout;
    private final Long minDaysSinceSwap;
    private final boolean async;

    public boolean isAcceptable(String status) {
        return acceptableStatuses.isAcceptable(status);
    }

    public Duration getTimeout() {
        return timeout;
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
