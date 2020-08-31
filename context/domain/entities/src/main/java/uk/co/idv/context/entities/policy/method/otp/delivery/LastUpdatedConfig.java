package uk.co.idv.context.entities.policy.method.otp.delivery;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class LastUpdatedConfig {

    private final boolean allowUnknown;
    private final Long minDaysSinceUpdate;

    public boolean isValid(Updatable updatable, Instant now) {
        Optional<Instant> lastUpdated = updatable.getLastUpdated();
        if (lastUpdated.isEmpty()) {
            return allowUnknown;
        }
        return calculateCutoff(now)
                .map(cutoff -> lastUpdated.get().isBefore(cutoff))
                .orElse(true);
    }

    public Optional<Long> getMinDaysSinceUpdate() {
        return Optional.ofNullable(minDaysSinceUpdate);
    }

    private Optional<Instant> calculateCutoff(Instant now) {
        return getMinDurationSinceUpdate().map(now::minus);
    }

    private Optional<Duration> getMinDurationSinceUpdate() {
        return getMinDaysSinceUpdate().map(Duration::ofDays);
    }

}
