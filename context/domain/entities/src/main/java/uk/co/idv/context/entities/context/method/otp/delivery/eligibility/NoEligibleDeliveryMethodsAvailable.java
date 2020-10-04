package uk.co.idv.context.entities.context.method.otp.delivery.eligibility;

import uk.co.idv.method.entities.eligibility.Ineligible;

public class NoEligibleDeliveryMethodsAvailable extends Ineligible {

    public NoEligibleDeliveryMethodsAvailable() {
        super("No eligible delivery methods available");
    }

}
