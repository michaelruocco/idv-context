package uk.co.idv.context.entities.lockout.policy.soft;


import java.time.Duration;

public interface SoftLockIntervalMother {

    static SoftLockInterval oneAttempt() {
        return build(1);
    }

    static SoftLockInterval build(int numberOfAttempts) {
        return new SoftLockInterval(numberOfAttempts, Duration.ofMinutes(numberOfAttempts));
    }

}
