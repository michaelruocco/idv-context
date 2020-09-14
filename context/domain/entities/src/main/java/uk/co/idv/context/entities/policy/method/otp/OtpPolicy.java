package uk.co.idv.context.entities.policy.method.otp;

import lombok.Builder;
import uk.co.idv.context.entities.policy.method.MethodPolicy;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigs;
import uk.co.idv.identity.entities.identity.RequestedData;

@Builder
public class OtpPolicy implements MethodPolicy {

    public static final String NAME = "one-time-passcode";

    private final OtpConfig methodConfig;
    private final DeliveryMethodConfigs deliveryMethodConfigs;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public OtpConfig getMethodConfig() {
        return methodConfig;
    }

    @Override
    public RequestedData getRequestedData() {
        return deliveryMethodConfigs.getRequestedData();
    }

    public DeliveryMethodConfigs getDeliveryMethodConfigs() {
        return deliveryMethodConfigs;
    }

}
