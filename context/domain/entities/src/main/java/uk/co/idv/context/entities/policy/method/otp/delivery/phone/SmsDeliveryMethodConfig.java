package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

public class SmsDeliveryMethodConfig extends PhoneDeliveryMethodConfig {

    public static final String TYPE = "sms";

    public SmsDeliveryMethodConfig(OtpPhoneNumberConfig phoneNumberConfig) {
        super(TYPE, phoneNumberConfig);
    }

    @Override
    public Collection<OtpPhoneNumber> filter(Collection<OtpPhoneNumber> numbers, Instant now) {
        Collection<OtpPhoneNumber> mobileNumbers = numbers.stream()
                .filter(OtpPhoneNumber::isMobile)
                .collect(Collectors.toList());
        return super.filter(mobileNumbers, now);
    }

}
