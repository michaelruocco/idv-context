package uk.co.idv.context.entities.policy.method.otp;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.method.MethodPolicy;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigs;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Duration;
import java.util.Optional;

@Builder
@Data
public class OtpPolicy implements MethodPolicy {

    public static final String NAME = "one-time-passcode";

    private final OtpConfig methodConfig;
    private final DeliveryMethodConfigs deliveryMethodConfigs;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public RequestedData getRequestedData() {
        return deliveryMethodConfigs.getRequestedData();
    }

    public boolean hasAsyncSimSwap() {
        return deliveryMethodConfigs.hasAsyncSimSwap();
    }

    public Optional<Duration> getLongestSimSwapConfigTimeout() {
        return deliveryMethodConfigs.getLongestSimSwapConfigTimeout();
    }

}
