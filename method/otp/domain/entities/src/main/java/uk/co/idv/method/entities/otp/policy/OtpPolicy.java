package uk.co.idv.method.entities.otp.policy;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.method.entities.otp.OtpName;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigs;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.time.Duration;
import java.util.Optional;

@Builder
@Data
public class OtpPolicy implements MethodPolicy {

    private final OtpConfig config;
    private final DeliveryMethodConfigs deliveryMethodConfigs;

    @Override
    public String getName() {
        return OtpName.NAME;
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
