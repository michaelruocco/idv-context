package uk.co.idv.context.lockout.policy.soft;

import java.time.Duration;

public interface SoftLockIntervalMother {

    static SoftLockInterval build(int numberOfAttempts) {
        return new SoftLockInterval(numberOfAttempts, Duration.ofMinutes(numberOfAttempts));
    }

}
