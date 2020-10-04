package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.eligibility.SimSwapStatusNotAllowed;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.eligibility.SimSwappedAfterCutoff;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.Eligible;

import java.time.Instant;
import java.util.Optional;

@Builder
@Data
@Slf4j
public class SimSwapResult {

    private final SimSwapConfig config;
    private final String status;
    private final Instant lastSwapped;

    public Eligibility toEligibility(Instant now) {
        return getLastSwapped()
                .map(swappedAt -> toLastSwappedEligibility(swappedAt, now))
                .orElse(toStatusEligibility());
    }

    public Optional<Instant> getLastSwapped() {
        return Optional.ofNullable(lastSwapped);
    }

    private Eligibility toLastSwappedEligibility(Instant swappedAt, Instant now) {
        Optional<Instant> cutoff = config.calculateCutoff(now);
        if (cutoff.isEmpty()) {
            return toStatusEligibility();
        }
        if (cutoff.map(swappedAt::isAfter).orElse(false)) {
            return new SimSwappedAfterCutoff(swappedAt, cutoff.get());
        }
        return toStatusEligibility();
    }

    private Eligibility toStatusEligibility() {
        if (config.isAcceptable(status)) {
            return new Eligible();
        }
        return new SimSwapStatusNotAllowed(status);
    }

}
