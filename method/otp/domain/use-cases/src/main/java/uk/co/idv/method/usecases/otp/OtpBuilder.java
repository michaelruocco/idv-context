package uk.co.idv.method.usecases.otp;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.method.entities.method.MethodsRequest;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.policy.OtpPolicy;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.idv.method.usecases.MethodBuilder;
import uk.co.idv.method.usecases.otp.delivery.DeliveryMethodConfigsConverter;
import uk.co.idv.method.usecases.otp.simswap.SimSwap;

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
                .deliveryMethods(buildDeliveryMethods(request, otpPolicy))
                .config(otpPolicy.getConfig())
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
