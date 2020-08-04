package uk.co.idv.context.entities.lockout.policy.soft;

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
