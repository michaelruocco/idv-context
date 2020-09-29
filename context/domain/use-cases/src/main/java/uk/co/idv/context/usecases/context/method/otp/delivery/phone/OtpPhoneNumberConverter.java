package uk.co.idv.context.usecases.context.method.otp.delivery.phone;

import lombok.Builder;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.PhoneDeliveryMethodConfig;

@Builder
public class OtpPhoneNumberConverter {

    private final IdGenerator idGenerator;
    private final OtpPhoneNumberEligibilityCalculator eligibilityCalculator;

    public DeliveryMethod toDeliveryMethod(OtpPhoneNumber number, PhoneDeliveryMethodConfig config) {
        return DeliveryMethod.builder()
                .id(idGenerator.generate())
                .type(config.getType())
                .value(number.getValue())
                .lastUpdated(number.getLastUpdated().orElse(null))
                .eligibility(eligibilityCalculator.toEligibility(number, config))
                .build();
    }

}
