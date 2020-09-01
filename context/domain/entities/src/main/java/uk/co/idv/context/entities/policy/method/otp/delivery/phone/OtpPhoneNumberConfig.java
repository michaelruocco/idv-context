package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.method.otp.delivery.LastUpdatedConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.time.Instant;
import java.util.Optional;

@Builder
@Data
public class OtpPhoneNumberConfig {

    private final boolean allowInternational;
    private final LastUpdatedConfig lastUpdatedConfig;
    private final SimSwapConfig simSwapConfig;

    public boolean isValid(OtpPhoneNumber number, Instant now) {
        if (allowInternational) {
            return lastUpdatedAllowed(number, now);
        }
        return number.isLocal() && lastUpdatedAllowed(number, now);
    }

    public Optional<SimSwapConfig> getSimSwapConfig() {
        return Optional.ofNullable(simSwapConfig);
    }

    private boolean lastUpdatedAllowed(OtpPhoneNumber number, Instant now) {
        return lastUpdatedConfig.isValid(number, now);
    }

}
