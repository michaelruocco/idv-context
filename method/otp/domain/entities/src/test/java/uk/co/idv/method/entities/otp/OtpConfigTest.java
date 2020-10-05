package uk.co.idv.method.entities.otp;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OtpConfigTest {

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        int maxNumberOfAttempts = 2;

        OtpConfig config = OtpConfig.builder()
                .maxNumberOfAttempts(maxNumberOfAttempts)
                .build();

        assertThat(config.getMaxNumberOfAttempts()).isEqualTo(maxNumberOfAttempts);
    }

    @Test
    void shouldReturnDuration() {
        Duration duration = Duration.ofMinutes(5);

        OtpConfig config = OtpConfig.builder()
                .duration(duration)
                .build();

        assertThat(config.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReturnPasscodeConfig() {
        PasscodeConfig passcodeConfig = mock(PasscodeConfig.class);

        OtpConfig config = OtpConfig.builder()
                .passcodeConfig(passcodeConfig)
                .build();

        assertThat(config.getPasscodeConfig()).isEqualTo(passcodeConfig);
    }

}
