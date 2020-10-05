package uk.co.idv.context.usecases.context.method.otp.delivery.phone;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;
import uk.co.mruoc.localphone.LocalNumberCalculator;
import uk.co.mruoc.localphone.LocalPhoneNumber;
import uk.co.mruoc.localphone.LocalPhoneNumberMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PhoneNumberConverterTest {

    private final LocalNumberCalculator calculator = mock(LocalNumberCalculator.class);

    private final PhoneNumberConverter converter = new PhoneNumberConverter(calculator);

    @Test
    void shouldPopulateLastUpdated() {
        CountryCode country = CountryCode.GB;
        PhoneNumber number = PhoneNumberMother.example();
        given(calculator.toLocalPhoneNumber(number.getValue(), country)).willReturn(mock(LocalPhoneNumber.class));

        OtpPhoneNumber otpNumber = converter.toOtpPhoneNumber(number, country);

        assertThat(otpNumber.getLastUpdated()).isEqualTo(number.getLastUpdated());
    }

    @Test
    void shouldPopulateEmptyLastUpdatedIfNotPresent() {
        CountryCode country = CountryCode.GB;
        PhoneNumber number = PhoneNumberMother.withoutLastUpdated();
        given(calculator.toLocalPhoneNumber(number.getValue(), country)).willReturn(mock(LocalPhoneNumber.class));

        OtpPhoneNumber otpNumber = converter.toOtpPhoneNumber(number, country);

        assertThat(otpNumber.getLastUpdated()).isEmpty();
    }

    @Test
    void shouldPopulateLocal() {
        CountryCode country = CountryCode.GB;
        PhoneNumber number = PhoneNumberMother.example();
        LocalPhoneNumber localNumber = LocalPhoneNumberMother.localMobile();
        given(calculator.toLocalPhoneNumber(number.getValue(), country)).willReturn(localNumber);

        OtpPhoneNumber otpNumber = converter.toOtpPhoneNumber(number, country);

        assertThat(otpNumber.isLocal()).isEqualTo(localNumber.isLocal());
    }

    @Test
    void shouldPopulateMobile() {
        CountryCode country = CountryCode.GB;
        PhoneNumber number = PhoneNumberMother.example();
        LocalPhoneNumber localNumber = LocalPhoneNumberMother.localMobile();
        given(calculator.toLocalPhoneNumber(number.getValue(), country)).willReturn(localNumber);

        OtpPhoneNumber otpNumber = converter.toOtpPhoneNumber(number, country);

        assertThat(otpNumber.isMobile()).isEqualTo(localNumber.isMobile());
    }

    @Test
    void shouldPopulateValueWithFormattedValue() {
        CountryCode country = CountryCode.GB;
        PhoneNumber number = PhoneNumberMother.example();
        LocalPhoneNumber localNumber = LocalPhoneNumberMother.localMobile();
        given(calculator.toLocalPhoneNumber(number.getValue(), country)).willReturn(localNumber);

        OtpPhoneNumber otpNumber = converter.toOtpPhoneNumber(number, country);

        assertThat(otpNumber.getValue()).isEqualTo(localNumber.getFormattedValue());
    }

}
