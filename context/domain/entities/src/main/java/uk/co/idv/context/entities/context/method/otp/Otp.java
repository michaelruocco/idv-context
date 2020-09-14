package uk.co.idv.context.entities.context.method.otp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.policy.method.MethodConfig;
import uk.co.idv.context.entities.policy.method.otp.OtpConfig;

@Builder
@Data
public class Otp implements Method {

    private final String name;
    private final DeliveryMethods deliveryMethods;

    @Getter(AccessLevel.NONE)
    private final OtpConfig otpConfig;

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

    @Override
    public MethodConfig getConfig() {
        return otpConfig;
    }

}
