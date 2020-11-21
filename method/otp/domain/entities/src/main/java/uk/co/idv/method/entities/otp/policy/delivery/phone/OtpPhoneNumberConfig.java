package uk.co.idv.method.entities.otp.policy.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.delivery.phone.eligibility.InternationalNumbersNotAllowed;
import uk.co.idv.method.entities.otp.policy.delivery.LastUpdatedConfig;

import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class OtpPhoneNumberConfig {

    private final CountryCode country;
    private final boolean allowInternational;
    private final LastUpdatedConfig lastUpdatedConfig;
    private final SimSwapConfig simSwapConfig;
    private final boolean maskNumbers;

    public Eligibility toEligibility(OtpPhoneNumber number, Instant now) {
        if (allowInternational || number.isLocal()) {
            return lastUpdatedConfig.toEligibility(number, now);
        }
        return new InternationalNumbersNotAllowed(country);
    }

    public Optional<SimSwapConfig> getSimSwapConfig() {
        return Optional.ofNullable(simSwapConfig);
    }

}
