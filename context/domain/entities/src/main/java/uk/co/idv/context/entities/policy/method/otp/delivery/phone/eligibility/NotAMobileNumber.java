package uk.co.idv.context.entities.policy.method.otp.delivery.phone.eligibility;

import uk.co.idv.context.entities.context.eligibility.Ineligible;

public class NotAMobileNumber extends Ineligible {

    public NotAMobileNumber() {
        super("not a mobile number");
    }

}
