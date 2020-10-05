package uk.co.idv.method.entities.otp.delivery.phone.simswap;

import uk.co.idv.method.entities.otp.delivery.DeliveryMethodsMother;
import uk.co.idv.method.entities.otp.policy.OtpPolicyMother;
import uk.co.idv.method.entities.otp.simswap.SimSwapRequest;

import java.util.UUID;

public interface SimSwapRequestMother {

    static SimSwapRequest build() {
        return builder().build();
    }

    static SimSwapRequest.SimSwapRequestBuilder builder() {
        return SimSwapRequest.builder()
                .deliveryMethods(DeliveryMethodsMother.oneOfEach())
                .contextId(UUID.fromString("16695193-59a9-4996-953f-b3ad715fedd6"))
                .policy(OtpPolicyMother.build());
    }

}
