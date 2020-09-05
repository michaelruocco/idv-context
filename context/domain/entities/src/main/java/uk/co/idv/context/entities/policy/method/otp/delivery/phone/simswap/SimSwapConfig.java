package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;

@Builder
@Data
public class SimSwapConfig {

    private final AcceptableSimSwapResults acceptableResults;
    private final Duration timeout;
    private final Long minDaysSinceSwap;

    public boolean isAcceptable(String result) {
        return acceptableResults.isAcceptable(result);
    }

    public Duration getTimeout() {
        return timeout;
    }

    public Long getMinDaysSinceSwap() {
        return minDaysSinceSwap;
    }

    public Instant calculateCutoff(Instant now) {
        return now.minus(getMinDurationSinceSwap());
    }

    private Duration getMinDurationSinceSwap() {
        return Duration.ofDays(minDaysSinceSwap);
    }

}
