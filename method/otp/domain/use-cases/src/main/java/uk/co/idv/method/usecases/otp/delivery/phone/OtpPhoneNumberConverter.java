package uk.co.idv.method.usecases.otp.delivery.phone;

import lombok.Builder;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.policy.delivery.phone.PhoneDeliveryMethodConfig;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

@Builder
public class OtpPhoneNumberConverter {

    private final UuidGenerator uuidGenerator;
    private final OtpPhoneNumberEligibilityCalculator eligibilityCalculator;

    public DeliveryMethod toDeliveryMethod(OtpPhoneNumber number, PhoneDeliveryMethodConfig config) {
        return DeliveryMethod.builder()
                .id(uuidGenerator.generate())
                .type(config.getType())
                .value(number.getValue())
                .lastUpdated(number.getLastUpdated().orElse(null))
                .eligibility(eligibilityCalculator.toEligibility(number, config))
                .build();
    }

}
