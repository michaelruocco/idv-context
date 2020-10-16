package uk.co.idv.lockout.entities.policy.soft;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;

@Builder
@Data
public class SoftLock {

    private final Duration duration;
    private final Instant start;

    public Instant calculateExpiry() {
        return start.plus(duration);
    }

}
