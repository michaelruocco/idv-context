package uk.co.idv.context.lockout.policy.soft;

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
    void shouldReturnExpiry() {
        Instant expiry = Instant.now();

        SoftLock softLock = SoftLock.builder()
                .expiry(expiry)
                .build();

        assertThat(softLock.getExpiry()).isEqualTo(expiry);
    }

}
