package uk.co.idv.context.lockout.policy.soft;

public interface SoftLockIntervalsMother {

    static SoftLockIntervals onlyOneAttempt() {
        return new SoftLockIntervals(SoftLockIntervalMother.build(1));
    }

}
