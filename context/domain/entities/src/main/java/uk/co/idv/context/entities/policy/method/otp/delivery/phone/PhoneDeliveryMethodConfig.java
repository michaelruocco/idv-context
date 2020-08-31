package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;

import java.time.Instant;
import java.util.Collection;

public interface PhoneDeliveryMethodConfig extends DeliveryMethodConfig {

    Collection<OtpPhoneNumber> filter(Collection<OtpPhoneNumber> numbers, Instant now);

}
