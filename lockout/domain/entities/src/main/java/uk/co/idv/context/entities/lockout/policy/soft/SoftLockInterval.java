package uk.co.idv.context.entities.lockout.policy.soft;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Builder
@Data
public class SoftLockInterval {

    private final int numberOfAttempts;
    private final Duration duration;

}
