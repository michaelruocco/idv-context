package uk.co.idv.lockout.entities.policy.soft;

public interface RecurringSoftLockoutStateCalculatorMother {

    static RecurringSoftLockoutStateCalculator build() {
        return new RecurringSoftLockoutStateCalculator(SoftLockIntervalMother.oneAttempt());
    }

}
