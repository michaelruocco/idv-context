package uk.co.idv.method.entities.otp.simswap.eligibility;

import lombok.Builder;
import uk.co.idv.method.entities.eligibility.AsyncEligibility;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;

import java.util.Optional;

@Builder
public class SimSwapEligibility implements AsyncEligibility {

    private final String status;
    private final SimSwapConfig config;

    @Override
    public boolean isEligible() {
        return config.isAcceptable(status);
    }

    @Override
    public Optional<String> getReason() {
        if (isEligible()) {
            return Optional.empty();
        }
        return Optional.of(toIneligibleReason(status));
    }

    @Override
    public boolean isComplete() {
        return true;
    }

    private static String toIneligibleReason(String status) {
        return String.format("sim swap status %s not acceptable", status);
    }

}
