package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.eligibility.NotAMobileNumber;

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
    void shouldReturnNotAMobileNumberEligibilityIfNumberIsNotAMobileNumber() {
        OtpPhoneNumber number = givenNonMobileNumber();
        Instant now = Instant.now();

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isInstanceOf(NotAMobileNumber.class);
    }

    @Test
    void shouldReturnEligibilityFromPhoneNumberConfigIfNumberIsAMobileNumber() {
        OtpPhoneNumber number = givenMobileNumber();
        Instant now = Instant.now();
        Eligibility expectedEligibility = mock(Eligibility.class);
        given(phoneNumberConfig.toEligibility(number, now)).willReturn(expectedEligibility);

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

    private OtpPhoneNumber givenNonMobileNumber() {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        given(number.isMobile()).willReturn(false);
        return number;
    }

    private OtpPhoneNumber givenMobileNumber() {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        given(number.isMobile()).willReturn(true);
        return number;
    }

}
