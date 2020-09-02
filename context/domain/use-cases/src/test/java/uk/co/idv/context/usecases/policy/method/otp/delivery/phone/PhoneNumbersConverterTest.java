package uk.co.idv.context.usecases.policy.method.otp.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumber;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumbers;

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
        OtpPhoneNumber expected1 = givenConvertedTo(country, number1);
        OtpPhoneNumber expected2 = givenConvertedTo(country, number2);

        OtpPhoneNumbers otpNumbers = numbersConverter.toOtpPhoneNumbers(country, numbers);

        assertThat(otpNumbers).containsExactly(
                expected1,
                expected2
        );
    }

    private OtpPhoneNumber givenConvertedTo(CountryCode country, PhoneNumber number) {
        OtpPhoneNumber otpNumber = mock(OtpPhoneNumber.class);
        given(numberConverter.toOtpPhoneNumber(country, number)).willReturn(otpNumber);
        return otpNumber;
    }

}
