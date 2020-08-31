package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.method.otp.delivery.LastUpdatedConfig;

import java.time.Instant;

@Builder
@Data
public class OtpPhoneNumberConfig {

    private final CountryCode region;
    private final boolean allowInternational;
    private final LastUpdatedConfig lastUpdatedConfig;

    public boolean isValid(OtpPhoneNumber number, Instant now) {
        if (allowInternational) {
            return lastUpdatedAllowed(number, now);
        }
        return !number.isInternational() && lastUpdatedAllowed(number, now);
    }

    private boolean lastUpdatedAllowed(OtpPhoneNumber number, Instant now) {
        return lastUpdatedConfig.isValid(number, now);
    }

}
