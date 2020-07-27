package uk.co.idv.context.entities.lockout.policy.soft;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;

@Builder
@Data
public class SoftLock {

    private final Duration duration;
    private final Instant expiry;

}
