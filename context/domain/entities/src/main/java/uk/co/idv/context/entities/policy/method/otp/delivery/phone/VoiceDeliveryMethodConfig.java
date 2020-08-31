package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
public class VoiceDeliveryMethodConfig implements PhoneDeliveryMethodConfig {

    public static final String TYPE = "voice";

    private final OtpPhoneNumberConfig phoneNumberConfig;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Collection<OtpPhoneNumber> filter(Collection<OtpPhoneNumber> numbers, Instant now) {
        return numbers.stream()
                .filter(number -> phoneNumberConfig.isValid(number, now))
                .collect(Collectors.toList());
    }

}
