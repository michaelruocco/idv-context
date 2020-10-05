package uk.co.idv.context.usecases.context.method.otp.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumbers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PhoneNumbersConverterTest {

    private final PhoneNumberConverter numberConverter = mock(PhoneNumberConverter.class);

    private final PhoneNumbersConverter numbersConverter = new PhoneNumbersConverter(numberConverter);

    @Test
    void shouldConvertMultipleNumbers() {
        CountryCode country = CountryCode.GB;
        PhoneNumber number1 = mock(PhoneNumber.class);
        PhoneNumber number2 = mock(PhoneNumber.class);
        PhoneNumbers numbers = PhoneNumbersMother.with(number1, number2);
        OtpPhoneNumber expected1 = givenConvertedTo(number1, country);
        OtpPhoneNumber expected2 = givenConvertedTo(number2, country);

        OtpPhoneNumbers otpNumbers = numbersConverter.toOtpPhoneNumbers(numbers, country);

        assertThat(otpNumbers).containsExactly(
                expected1,
                expected2
        );
    }

    private OtpPhoneNumber givenConvertedTo(PhoneNumber number, CountryCode country) {
        OtpPhoneNumber otpNumber = mock(OtpPhoneNumber.class);
        given(numberConverter.toOtpPhoneNumber(number, country)).willReturn(otpNumber);
        return otpNumber;
    }

}
