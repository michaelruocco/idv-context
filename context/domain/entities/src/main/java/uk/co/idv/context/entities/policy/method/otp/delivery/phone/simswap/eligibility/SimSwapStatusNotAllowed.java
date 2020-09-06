package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.eligibility;

import uk.co.idv.context.entities.context.eligibility.Ineligible;

public class SimSwapStatusNotAllowed extends Ineligible {

    public SimSwapStatusNotAllowed(String status) {
        super(String.format("sim swap status %s is not allowed", status));
    }

}
