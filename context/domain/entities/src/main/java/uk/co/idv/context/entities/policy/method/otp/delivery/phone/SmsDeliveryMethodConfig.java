package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfig;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Data
public class SmsDeliveryMethodConfig implements PhoneDeliveryMethodConfig {

    public static final String TYPE = "sms";

    private final OtpPhoneNumberConfig phoneNumberConfig;
    private final SimSwapConfig simSwapConfig;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Collection<OtpPhoneNumber> filter(Collection<OtpPhoneNumber> numbers, Instant now) {
        return numbers.stream()
                .filter(OtpPhoneNumber::isMobile)
                .filter(number -> phoneNumberConfig.isValid(number, now))
                .collect(Collectors.toList());
    }

    public Optional<SimSwapConfig> getSimSwapConfig() {
        return Optional.ofNullable(simSwapConfig);
    }

}
