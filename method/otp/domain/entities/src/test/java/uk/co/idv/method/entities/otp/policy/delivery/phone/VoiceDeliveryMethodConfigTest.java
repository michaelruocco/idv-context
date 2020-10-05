package uk.co.idv.method.entities.otp.policy.delivery.phone;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumber;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class VoiceDeliveryMethodConfigTest {

    private final OtpPhoneNumberConfig phoneNumberConfig = mock(OtpPhoneNumberConfig.class);

    private final VoiceDeliveryMethodConfig config = new VoiceDeliveryMethodConfig(phoneNumberConfig);

    @Test
    void shouldReturnType() {
        assertThat(config.getType()).isEqualTo("voice");
    }

    @Test
    void shouldReturnPhoneNumberConfig() {
        assertThat(config.getPhoneNumberConfig()).isEqualTo(phoneNumberConfig);
    }

    @Test
    void shouldReturnEligibilityFromPhoneNumberConfig() {
        OtpPhoneNumber number = mock(OtpPhoneNumber.class);
        Instant now = Instant.now();
        Eligibility expectedEligibility = mock(Eligibility.class);
        given(phoneNumberConfig.toEligibility(number, now)).willReturn(expectedEligibility);

        Eligibility eligibility = config.toEligibility(number, now);

        assertThat(eligibility).isEqualTo(expectedEligibility);
    }

}
