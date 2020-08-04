package uk.co.idv.context.entities.lockout.policy.nonlocking;

public interface NonLockingStateCalculatorMother {

    static NonLockingStateCalculator build() {
        return new NonLockingStateCalculator();
    }

}
