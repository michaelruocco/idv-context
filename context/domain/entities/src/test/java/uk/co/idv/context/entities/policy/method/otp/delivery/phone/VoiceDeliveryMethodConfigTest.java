package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;

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
    void shouldFilterValidPhoneNumbers() {
        Instant now = Instant.now();
        OtpPhoneNumber invalid = givenInvalidNumber(now);
        OtpPhoneNumber valid = givenValidNumber(now);

        Collection<OtpPhoneNumber> filtered = config.filter(Arrays.asList(invalid, valid), now);

        assertThat(filtered).containsExactly(valid);
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
