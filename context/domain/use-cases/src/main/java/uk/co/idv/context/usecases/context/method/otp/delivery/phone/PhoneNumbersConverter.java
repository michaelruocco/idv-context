package uk.co.idv.context.usecases.context.method.otp.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumbers;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PhoneNumbersConverter {

    private final PhoneNumberConverter converter;

    public OtpPhoneNumbers toOtpPhoneNumbers(PhoneNumbers numbers, CountryCode country) {
        return new OtpPhoneNumbers(numbers.stream()
                .map(number -> converter.toOtpPhoneNumber(number, country))
                .collect(Collectors.toList()));
    }

}
