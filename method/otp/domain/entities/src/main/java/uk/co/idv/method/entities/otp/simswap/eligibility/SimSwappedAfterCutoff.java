package uk.co.idv.method.entities.otp.simswap.eligibility;


import uk.co.idv.method.entities.eligibility.Ineligible;

import java.time.Instant;

public class SimSwappedAfterCutoff extends Ineligible {

    public SimSwappedAfterCutoff(Instant swappedAt, Instant cutoff) {
        super(String.format("sim swapped at %s (cutoff %s)", swappedAt, cutoff));
    }

}
