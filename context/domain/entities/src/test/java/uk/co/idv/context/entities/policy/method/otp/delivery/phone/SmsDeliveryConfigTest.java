package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SmsDeliveryConfigTest {

    private final OtpPhoneNumberConfig phoneNumberConfig = mock(OtpPhoneNumberConfig.class);

    private final PhoneDeliveryConfig config = new SmsDeliveryConfig(phoneNumberConfig);

    @Test
    void shouldReturnName() {
        assertThat(config.getName()).isEqualTo("sms");
    }

    @Test
    void shouldFilterValidPhoneMobileNumbers() {
        Instant now = Instant.now();
        OtpPhoneNumber invalid = givenInvalidNumber(now);
        OtpPhoneNumber valid = givenValidNumber(now);
        OtpPhoneNumber validMobile = givenValidMobileNumber(now);

        Collection<OtpPhoneNumber> filtered = config.filter(Arrays.asList(invalid, valid, validMobile), now);

        assertThat(filtered).containsExactly(validMobile);
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
