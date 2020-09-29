package uk.co.idv.context.entities.context.method.otp.simswap;

import uk.co.idv.context.entities.context.method.MethodsRequestMother;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethodsMother;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicyMother;

public interface SimSwapRequestMother {

    static SimSwapRequest build() {
        return builder().build();
    }

    static SimSwapRequest.SimSwapRequestBuilder builder() {
        return SimSwapRequest.builder()
                .deliveryMethods(DeliveryMethodsMother.oneOfEach())
                .methodsRequest(MethodsRequestMother.build())
                .policy(OtpPolicyMother.build());
    }

}
