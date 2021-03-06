package uk.co.idv.method.usecases.otp.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumbers;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PhoneNumbersConverter {

    private final PhoneNumberConverter converter;

    public PhoneNumbersConverter() {
        this(new PhoneNumberConverter());
    }

    public OtpPhoneNumbers toOtpPhoneNumbers(PhoneNumbers numbers, CountryCode country) {
        return new OtpPhoneNumbers(numbers.stream()
                .map(number -> converter.toOtpPhoneNumber(number, country))
                .collect(Collectors.toList()));
    }

}
