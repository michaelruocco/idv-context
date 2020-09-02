package uk.co.idv.lockout.entities.policy.soft;

public interface SoftLockoutStateCalculatorMother {

    static SoftLockoutStateCalculator build() {
        return new SoftLockoutStateCalculator(SoftLockIntervalsMother.oneAndTwoAttempts());
    }

}
