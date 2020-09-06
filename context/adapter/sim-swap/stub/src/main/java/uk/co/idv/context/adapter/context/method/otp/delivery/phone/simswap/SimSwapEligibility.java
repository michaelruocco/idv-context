package uk.co.idv.context.adapter.context.method.otp.delivery.phone.simswap;

import lombok.Builder;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import java.time.Instant;
import java.util.Optional;

@Builder
public class SimSwapEligibility implements Eligibility {

    private final Instant now;
    private final AsyncSimSwapResult result;

    @Override
    public boolean isEligible() {
        return getResultEligibility().isEligible();
    }

    @Override
    public Optional<String> getReason() {
        return getResultEligibility().getReason();
    }

    private Eligibility getResultEligibility() {
        return result.toEligibility(now);
    }

}
