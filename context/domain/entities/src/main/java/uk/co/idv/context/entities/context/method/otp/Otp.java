package uk.co.idv.context.entities.context.method.otp;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;

@Builder
@Data
public class Otp implements Method {

    private final String name;
    private final DeliveryMethods deliveryMethods;

    @Override
    public Eligibility getEligibility() {
        return deliveryMethods.getEligibility();
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

}
