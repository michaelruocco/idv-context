package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.eligibility.NotAMobileNumber;

import java.time.Instant;

public class SmsDeliveryMethodConfig extends PhoneDeliveryMethodConfig {

    public static final String TYPE = "sms";

    public SmsDeliveryMethodConfig(OtpPhoneNumberConfig phoneNumberConfig) {
        super(TYPE, phoneNumberConfig);
    }

    @Override
    public Eligibility toEligibility(OtpPhoneNumber number, Instant now) {
        if (!number.isMobile()) {
            return new NotAMobileNumber();
        }
        return super.toEligibility(number, now);
    }

}
