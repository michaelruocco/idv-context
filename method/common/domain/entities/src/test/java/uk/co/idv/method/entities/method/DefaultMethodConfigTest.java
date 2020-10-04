package uk.co.idv.method.entities.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultMethodConfigTest {

    @Test
    void shouldReturnMaxNumberOfAttempts() {
        int maxNumberOfAttempts = 2;

        MethodConfig config = DefaultMethodConfig.builder()
                .maxNumberOfAttempts(maxNumberOfAttempts)
                .build();

        assertThat(config.getMaxNumberOfAttempts()).isEqualTo(maxNumberOfAttempts);
    }

    @Test
    void shouldReturnDuration() {
        Duration duration = Duration.ofMinutes(5);

        MethodConfig config = DefaultMethodConfig.builder()
                .duration(duration)
                .build();

        assertThat(config.getDuration()).isEqualTo(duration);
    }

}
