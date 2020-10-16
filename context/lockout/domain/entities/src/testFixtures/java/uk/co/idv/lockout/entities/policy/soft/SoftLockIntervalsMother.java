package uk.co.idv.lockout.entities.policy.soft;

public interface SoftLockIntervalsMother {

    static SoftLockIntervals oneAttempt() {
        return new SoftLockIntervals(SoftLockIntervalMother.build(1));
    }

    static SoftLockIntervals oneAndTwoAttempts() {
        return new SoftLockIntervals(
                SoftLockIntervalMother.build(1),
                SoftLockIntervalMother.build(2)
        );
    }

}
