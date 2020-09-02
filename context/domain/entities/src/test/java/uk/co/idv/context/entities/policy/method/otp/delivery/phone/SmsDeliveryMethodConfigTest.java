package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.policy.method.otp.delivery.OtpPhoneNumbersMother;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SmsDeliveryMethodConfigTest {

    private final OtpPhoneNumberConfig phoneNumberConfig = mock(OtpPhoneNumberConfig.class);

    private final SmsDeliveryMethodConfig config = new SmsDeliveryMethodConfig(phoneNumberConfig);

    @Test
    void shouldReturnType() {
        assertThat(config.getType()).isEqualTo("sms");
    }

    @Test
    void shouldReturnPhoneNumberConfig() {
        assertThat(config.getPhoneNumberConfig()).isEqualTo(phoneNumberConfig);
    }

    @Test
    void shouldFilterValidPhoneMobileNumbers() {
        Instant now = Instant.now();
        OtpPhoneNumber invalid = givenInvalidNumber(now);
        OtpPhoneNumber valid = givenValidNumber(now);
        OtpPhoneNumber validMobile = givenValidMobileNumber(now);

        OtpPhoneNumbers filtered = config.filter(OtpPhoneNumbersMother.with(invalid, valid, validMobile), now);

        assertThat(filtered).containsExactly(validMobile);
    }

    @Test
    void shouldRequestPhoneNumbers() {
        RequestedData requestedData = config.getRequestedData();

        assertThat(requestedData).isEqualTo(RequestedData.phoneNumbersOnly());
    }

    private OtpPhoneNumber givenValidMobileNumber(Instant now) {
        OtpPhoneNumber number = givenValidNumber(now);
        given(number.isMobile()).willReturn(true);
        return number;
    }

    private OtpPhoneNumber givenValidNumber(Instant now) {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        given(phoneNumberConfig.isValid(number, now)).willReturn(true);
        return number;
    }

    private OtpPhoneNumber givenInvalidNumber(Instant now) {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        given(phoneNumberConfig.isValid(number, now)).willReturn(false);
        return number;
    }

}
