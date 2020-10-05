package uk.co.idv.method.entities.otp.simswap.eligibility;

import uk.co.idv.method.entities.eligibility.Ineligible;

public class SimSwapStatusNotAllowed extends Ineligible {

    public SimSwapStatusNotAllowed(String status) {
        super(String.format("sim swap status %s is not allowed", status));
    }

}
