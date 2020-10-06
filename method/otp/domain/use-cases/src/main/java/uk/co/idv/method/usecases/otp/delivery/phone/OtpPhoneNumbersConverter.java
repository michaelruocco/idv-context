package uk.co.idv.method.usecases.otp.delivery.phone;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumbers;
import uk.co.idv.method.entities.otp.policy.delivery.phone.PhoneDeliveryMethodConfig;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OtpPhoneNumbersConverter {

    private final OtpPhoneNumberConverter numberConverter;

    public DeliveryMethods toDeliveryMethods(OtpPhoneNumbers numbers, PhoneDeliveryMethodConfig config) {
        return new DeliveryMethods(numbers.stream()
                .map(number -> numberConverter.toDeliveryMethod(number, config))
                .collect(Collectors.toList())
        );
    }

}
