package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VoiceDeliveryConfig implements PhoneDeliveryConfig {

    public static final String VOICE = "voice";

    private final OtpPhoneNumberConfig phoneNumberConfig;

    @Override
    public String getName() {
        return VOICE;
    }

    @Override
    public Collection<OtpPhoneNumber> filter(Collection<OtpPhoneNumber> numbers, Instant now) {
        return numbers.stream()
                .filter(number -> phoneNumberConfig.isValid(number, now))
                .collect(Collectors.toList());
    }

}
