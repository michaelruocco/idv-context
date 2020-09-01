package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;

import java.time.Instant;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Data
public class PhoneDeliveryMethodConfig implements DeliveryMethodConfig {

    private final String type;
    private final OtpPhoneNumberConfig phoneNumberConfig;

    public OtpPhoneNumbers filter(OtpPhoneNumbers numbers, Instant now) {
        Stream<OtpPhoneNumber> filtered = filter(numbers.stream(), now);
        return new OtpPhoneNumbers(filtered.collect(Collectors.toList()));
    }

    public Stream<OtpPhoneNumber> filter(Stream<OtpPhoneNumber> numbers, Instant now) {
        return numbers.filter(number -> phoneNumberConfig.isValid(number, now));
    }

}
