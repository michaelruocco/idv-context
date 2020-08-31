package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SmsDeliveryConfig implements PhoneDeliveryConfig {

    public static final String SMS = "sms";

    private final OtpPhoneNumberConfig phoneNumberConfig;

    @Override
    public String getName() {
        return SMS;
    }

    @Override
    public Collection<OtpPhoneNumber> filter(Collection<OtpPhoneNumber> numbers, Instant now) {
        return numbers.stream()
                .filter(OtpPhoneNumber::isMobile)
                .filter(number -> phoneNumberConfig.isValid(number, now))
                .collect(Collectors.toList());
    }

}
