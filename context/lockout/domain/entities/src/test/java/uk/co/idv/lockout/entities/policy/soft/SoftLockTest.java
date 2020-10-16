package uk.co.idv.lockout.entities.policy.soft;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockTest {

    @Test
    void shouldReturnDuration() {
        Duration duration = Duration.ZERO;

        SoftLock softLock = SoftLock.builder()
                .duration(duration)
                .build();

        assertThat(softLock.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldReturnStart() {
        Instant start = Instant.parse("2020-08-19T23:15:23.060Z");

        SoftLock softLock = SoftLock.builder()
                .start(start)
                .build();

        assertThat(softLock.getStart()).isEqualTo(start);
    }

    @Test
    void shouldCalculateExpiry() {
        SoftLock softLock = SoftLock.builder()
                .start(Instant.parse("2020-08-19T23:15:23.060Z"))
                .duration(Duration.ofMinutes(1))
                .build();

        Instant expiry = softLock.calculateExpiry();

        assertThat(expiry).isEqualTo(Instant.parse("2020-08-19T23:16:23.060Z"));
    }

}
