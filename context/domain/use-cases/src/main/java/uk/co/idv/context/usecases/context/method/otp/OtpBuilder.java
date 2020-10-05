package uk.co.idv.context.usecases.context.method.otp;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.method.MethodsRequest;
import uk.co.idv.context.usecases.context.method.MethodBuilder;
import uk.co.idv.context.usecases.context.method.otp.delivery.DeliveryMethodConfigsConverter;
import uk.co.idv.context.usecases.context.method.otp.simswap.SimSwap;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.policy.OtpPolicy;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;
import uk.co.idv.method.entities.policy.MethodPolicy;

@Builder
@Slf4j
public class OtpBuilder implements MethodBuilder {

    private final DeliveryMethodConfigsConverter configsConverter;
    private final SimSwap simSwap;

    @Override
    public boolean supports(MethodPolicy policy) {
        return policy instanceof OtpPolicy;
    }

    @Override
    public Otp build(MethodsRequest request, MethodPolicy policy) {
        OtpPolicy otpPolicy = (OtpPolicy) policy;
        return Otp.builder()
                .name(policy.getName())
                .deliveryMethods(buildDeliveryMethods(request, otpPolicy))
                .otpConfig(otpPolicy.getConfig())
                .build();
    }

    private DeliveryMethods buildDeliveryMethods(MethodsRequest request, OtpPolicy otpPolicy) {
        DeliveryMethods deliveryMethods = configsConverter.toDeliveryMethods(request.getIdentity(), otpPolicy.getDeliveryMethodConfigs());
        SimSwapRequest simSwapRequest = SimSwapRequest.builder()
                .contextId(request.getContextId())
                .deliveryMethods(deliveryMethods)
                .policy(otpPolicy)
                .build();
        simSwap.waitForSimSwapsIfRequired(simSwapRequest);
        return deliveryMethods;
    }

}
