package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.AcceptableSimSwapStatuses;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.eligibility.SimSwapStatusNotAllowed;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.eligibility.SimSwappedAfterCutoff;

import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class SimSwapResult {

    private final SimSwapConfig config;
    private final String status;
    private final Instant lastSwapped;

    public static SimSwapResult timeout() {
        return SimSwapResult.builder()
                .status(AcceptableSimSwapStatuses.TIMEOUT)
                .build();
    }

    public static SimSwapResult unknown() {
        return SimSwapResult.builder()
                .status(AcceptableSimSwapStatuses.UNKNOWN)
                .build();
    }

    public static SimSwapResult failure() {
        return SimSwapResult.builder()
                .status(AcceptableSimSwapStatuses.FAILURE)
                .build();
    }

    public static SimSwapResult successful() {
        return successful(null);
    }

    public static SimSwapResult successful(Instant lastSwapped) {
        return SimSwapResult.builder()
                .status(AcceptableSimSwapStatuses.SUCCESS)
                .lastSwapped(lastSwapped)
                .build();
    }

    public Eligibility toEligibility(Instant now) {
        return getLastSwapped()
                .map(swappedAt -> toLastSwappedEligibility(swappedAt, now))
                .orElse(toStatusEligibility());
    }

    private Eligibility toLastSwappedEligibility(Instant swappedAt, Instant now) {
        if (swappedAt.isAfter(config.calculateCutoff(now))) {
            return new SimSwappedAfterCutoff(swappedAt, now);
        }
        return toStatusEligibility();
    }

    private Eligibility toStatusEligibility() {
        if (config.isAcceptable(status)) {
            return new Eligible();
        }
        return new SimSwapStatusNotAllowed(status);
    }

    private Optional<Instant> getLastSwapped() {
        return Optional.ofNullable(lastSwapped);
    }

}
