package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Data
public class PhoneDeliveryMethodConfig implements DeliveryMethodConfig {

    private final String type;
    private final OtpPhoneNumberConfig phoneNumberConfig;

    public Collection<OtpPhoneNumber> filter(Collection<OtpPhoneNumber> numbers, Instant now) {
        return numbers.stream()
                .filter(number -> phoneNumberConfig.isValid(number, now))
                .collect(Collectors.toList());
    }

}
