package uk.co.idv.context.entities.policy.method.otp.delivery.eligibility;

import uk.co.idv.method.entities.eligibility.Ineligible;

import java.time.Instant;

public class UpdatedAfterCutoff extends Ineligible {

    public UpdatedAfterCutoff(Instant updatedAt, Instant cutoff) {
        super(String.format("updated at %s (cutoff %s)", updatedAt, cutoff));
    }

}
