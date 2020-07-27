package uk.co.idv.context.entities.lockout.policy.soft;

public interface SoftLockIntervalsMother {

    static SoftLockIntervals onlyOneAttempt() {
        return new SoftLockIntervals(SoftLockIntervalMother.build(1));
    }

}
