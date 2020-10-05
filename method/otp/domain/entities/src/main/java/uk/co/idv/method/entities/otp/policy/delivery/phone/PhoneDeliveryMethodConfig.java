package uk.co.idv.method.entities.otp.policy.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;

import java.time.Duration;
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

    public CountryCode getCountry() {
        return phoneNumberConfig.getCountry();
    }

    public Optional<Duration> getSimSwapTimeout() {
        return getSimSwapConfig().map(SimSwapConfig::getTimeout);
    }

    public Optional<SimSwapConfig> getSimSwapConfig() {
        return phoneNumberConfig.getSimSwapConfig();
    }

    public Eligibility toEligibility(OtpPhoneNumber number, Instant now) {
        return phoneNumberConfig.toEligibility(number, now);
    }

}
