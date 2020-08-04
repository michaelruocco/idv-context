package uk.co.idv.context.entities.lockout.policy.soft;

public interface RecurringSoftLockoutStateCalculatorMother {

    static RecurringSoftLockoutStateCalculator build() {
        return new RecurringSoftLockoutStateCalculator(SoftLockIntervalMother.oneAttempt());
    }

}
