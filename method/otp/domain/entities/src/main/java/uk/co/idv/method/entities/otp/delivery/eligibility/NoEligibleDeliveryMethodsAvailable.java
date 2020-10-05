package uk.co.idv.method.entities.otp.delivery;

import uk.co.idv.method.entities.eligibility.Ineligible;

public class NoEligibleDeliveryMethodsAvailable extends Ineligible {

    public NoEligibleDeliveryMethodsAvailable() {
        super("No eligible delivery methods available");
    }

}
