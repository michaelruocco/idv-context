package uk.co.idv.method.entities.otp.delivery.eligibility;

import uk.co.idv.method.entities.eligibility.Ineligible;

public class UnknownLastUpdatedNotAllowed extends Ineligible {

    public UnknownLastUpdatedNotAllowed() {
        super("unknown last updated date is not allowed");
    }

}
