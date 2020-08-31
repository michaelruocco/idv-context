package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryConfig;

import java.time.Instant;
import java.util.Collection;

public interface PhoneDeliveryConfig extends DeliveryConfig {

    Collection<OtpPhoneNumber> filter(Collection<OtpPhoneNumber> numbers, Instant now);

}
