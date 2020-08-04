package uk.co.idv.context.entities.lockout.policy.soft;

public interface SoftLockoutStateCalculatorMother {

    static SoftLockoutStateCalculator build() {
        return new SoftLockoutStateCalculator(SoftLockIntervalsMother.oneAndTwoAttempts());
    }

}
