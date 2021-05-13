package uk.co.idv.method.entities.push;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationConfigTest {

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        int maxNumberOfAttempts = 2;

        PushNotificationConfig config = PushNotificationConfig.builder()
                .maxNumberOfAttempts(maxNumberOfAttempts)
                .build();

        assertThat(config.getMaxNumberOfAttempts()).isEqualTo(maxNumberOfAttempts);
    }

    @Test
    void shouldReturnDuration() {
        Duration duration = Duration.ofMinutes(5);

        PushNotificationConfig config = PushNotificationConfig.builder()
                .duration(duration)
                .build();

        assertThat(config.getDuration()).isEqualTo(duration);
    }
}
