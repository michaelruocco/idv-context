package uk.co.idv.context.entities.policy.method.otp.delivery.phone;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class OtpPhoneNumberTest {

    @Test
    void shouldReturnValue() {
        String value = "my-value";

        OtpPhoneNumber number = OtpPhoneNumber.builder()
                .value(value)
                .build();

        assertThat(number.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnIsMobile() {
        OtpPhoneNumber number = OtpPhoneNumber.builder()
                .mobile(true)
                .build();

        assertThat(number.isMobile()).isTrue();
    }

    @Test
    void shouldReturnIsLocal() {
        OtpPhoneNumber number = OtpPhoneNumber.builder()
                .local(true)
                .build();

        assertThat(number.isLocal()).isTrue();
    }

    @Test
    void shouldReturnLastUpdated() {
        Instant lastUpdated = Instant.now();

        OtpPhoneNumber number = OtpPhoneNumber.builder()
                .lastUpdated(lastUpdated)
                .build();

        assertThat(number.getLastUpdated()).contains(lastUpdated);
    }

    @Test
    void shouldEmptyLastUpdatedIfNotSet() {
        OtpPhoneNumber number = OtpPhoneNumber.builder()
                .build();

        assertThat(number.getLastUpdated()).isEmpty();
    }

}
