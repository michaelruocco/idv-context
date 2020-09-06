package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Data
public class PhoneDeliveryMethodConfig implements DeliveryMethodConfig {

    private final String type;
    private final OtpPhoneNumberConfig phoneNumberConfig;

    @Override
    public RequestedData getRequestedData() {
        return RequestedData.phoneNumbersOnly();
    }

    public Optional<SimSwapConfig> getSimSwapConfig() {
        return phoneNumberConfig.getSimSwapConfig();
    }

    public Eligibility toEligibility(OtpPhoneNumber number, Instant now) {
        return phoneNumberConfig.toEligibility(number, now);
    }

}
