package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.eligibility;

import uk.co.idv.context.entities.context.eligibility.Ineligible;

import java.time.Instant;

public class SimSwappedAfterCutoff extends Ineligible {

    public SimSwappedAfterCutoff(Instant swappedAt, Instant cutoff) {
        super(String.format("sim swapped at %s (cutoff %s)", swappedAt, cutoff));
    }

}
