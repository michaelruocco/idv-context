package uk.co.idv.context.usecases.context.method.otp;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.common.usecases.async.FutureWaiter;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.otp.delivery.eligibility.EligibilityFutures;
import uk.co.idv.context.entities.policy.method.MethodPolicy;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicy;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigs;
import uk.co.idv.context.usecases.context.method.MethodBuilder;
import uk.co.idv.context.usecases.context.method.otp.delivery.DeliveryMethodConfigsConverter;
import uk.co.idv.identity.entities.identity.Identity;

import java.time.Duration;
import java.util.Optional;

@Builder
@Slf4j
public class OtpBuilder implements MethodBuilder {

    private final DeliveryMethodConfigsConverter configsConverter;
    private final FutureWaiter futureWaiter;

    @Override
    public boolean supports(MethodPolicy policy) {
        return policy instanceof OtpPolicy;
    }

    @Override
    public Otp build(Identity identity, MethodPolicy policy) {
        OtpPolicy otpPolicy = (OtpPolicy) policy;
        DeliveryMethodConfigs configs = otpPolicy.getDeliveryMethodConfigs();
        DeliveryMethods methods = configsConverter.toDeliveryMethods(identity, configs);
        waitForAnyAsyncSimSwaps(configs, methods);
        return Otp.builder()
                .name(otpPolicy.getName())
                .deliveryMethods(methods)
                .build();
    }

    private void waitForAnyAsyncSimSwaps(DeliveryMethodConfigs configs, DeliveryMethods methods) {
        Optional<Duration> timeout = configs.getLongestSimSwapConfigTimeout();
        EligibilityFutures futures = methods.toFutures();
        if (timeout.isPresent() && !futures.isEmpty()) {
            futureWaiter.waitFor(futures.all(), timeout.get());
        }
    }

}
