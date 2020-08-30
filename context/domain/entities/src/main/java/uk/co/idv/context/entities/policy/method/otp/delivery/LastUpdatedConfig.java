package uk.co.idv.context.entities.policy.method.otp.delivery;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.phonenumber.PhoneNumber;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class LastUpdatedConfig {

    private final boolean allowUnknown;
    private final Long cutoffDays;

    public boolean isValid(PhoneNumber phoneNumber, Instant now) {
        Optional<Instant> lastUpdated = phoneNumber.getLastUpdated();
        if (lastUpdated.isEmpty()) {
            return allowUnknown;
        }
        return calculateCutoff(now)
                .map(cutoff -> lastUpdated.get().isBefore(cutoff))
                .orElse(true);
    }

    public Optional<Long> getCutoffDays() {
        return Optional.ofNullable(cutoffDays);
    }

    private Optional<Instant> calculateCutoff(Instant now) {
        return getCutoffDuration().map(now::minus);
    }

    public Optional<Duration> getCutoffDuration() {
        return getCutoffDays().map(Duration::ofDays);
    }

}
