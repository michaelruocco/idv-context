package uk.co.idv.context.entities.policy.method.otp.delivery;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.policy.method.otp.delivery.eligibility.UpdatedAfterCutoff;
import uk.co.idv.context.entities.policy.method.otp.delivery.eligibility.UnknownLastUpdatedNotAllowed;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class LastUpdatedConfig {

    private final boolean allowUnknown;
    private final Long minDaysSinceUpdate;

    public Eligibility toEligibility(Updatable updatable, Instant now) {
        Optional<Instant> lastUpdated = updatable.getLastUpdated();
        if (lastUpdated.isEmpty()) {
            return toUnknownLastUpdatedEligibility();
        }
        return calculateCutoff(now)
                .map(cutoff -> toLastUpdatedEligibility(lastUpdated.get(), cutoff))
                .orElseGet(Eligible::new);
    }

    private Eligibility toUnknownLastUpdatedEligibility() {
        if (allowUnknown) {
            return new Eligible();
        }
        return new UnknownLastUpdatedNotAllowed();
    }

    private Eligibility toLastUpdatedEligibility(Instant lastUpdated, Instant cutoff) {
        if (lastUpdated.isAfter(cutoff)) {
            return new UpdatedAfterCutoff(lastUpdated, cutoff);
        }
        return new Eligible();
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
