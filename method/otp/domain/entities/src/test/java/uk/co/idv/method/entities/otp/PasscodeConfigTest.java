package uk.co.idv.context.entities.policy.method.otp;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PasscodeConfigTest {

    @Test
    void shouldReturnLength() {
        int length = 3;

        PasscodeConfig config = PasscodeConfig.builder()
                .length(length)
                .build();

        assertThat(config.getLength()).isEqualTo(length);
    }

    @Test
    void shouldReturnDuration() {
        Duration duration = Duration.ofMinutes(5);

        PasscodeConfig config = PasscodeConfig.builder()
                .duration(duration)
                .build();

        assertThat(config.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReturnPasscodeConfig() {
        int maxNumberOfDeliveries = 2;

        PasscodeConfig config = PasscodeConfig.builder()
                .maxNumberOfDeliveries(maxNumberOfDeliveries)
                .build();

        assertThat(config.getMaxNumberOfDeliveries()).isEqualTo(maxNumberOfDeliveries);
    }

}
