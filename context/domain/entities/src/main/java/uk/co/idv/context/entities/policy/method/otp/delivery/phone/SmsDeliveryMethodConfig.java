package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import java.time.Instant;
import java.util.stream.Stream;

public class SmsDeliveryMethodConfig extends PhoneDeliveryMethodConfig {

    public static final String TYPE = "sms";

    public SmsDeliveryMethodConfig(OtpPhoneNumberConfig phoneNumberConfig) {
        super(TYPE, phoneNumberConfig);
    }

    @Override
    public Stream<OtpPhoneNumber> filter(Stream<OtpPhoneNumber> numbers, Instant now) {
        Stream<OtpPhoneNumber> mobileNumbers = filterMobileNumber(numbers);
        return super.filter(mobileNumbers, now);
    }

    private Stream<OtpPhoneNumber> filterMobileNumber(Stream<OtpPhoneNumber> numbers) {
        return numbers.filter(OtpPhoneNumber::isMobile);
    }

}
