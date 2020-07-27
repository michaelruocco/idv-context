package uk.co.idv.context.entities.lockout.policy.soft;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockIntervalTest {

    @Test
    void shouldReturnNumberOfAttempts() {
        int numberOfAttempts = 2;

        SoftLockInterval interval = SoftLockInterval.builder()
                .numberOfAttempts(numberOfAttempts)
                .build();

        assertThat(interval.getNumberOfAttempts()).isEqualTo(numberOfAttempts);
    }

    @Test
    void shouldReturnDuration() {
        Duration duration = Duration.ZERO;

        SoftLockInterval interval = SoftLockInterval.builder()
                .duration(duration)
                .build();

        assertThat(interval.getDuration()).isEqualTo(duration);
    }

}
