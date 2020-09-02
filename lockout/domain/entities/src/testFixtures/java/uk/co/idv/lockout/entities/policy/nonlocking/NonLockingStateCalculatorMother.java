package uk.co.idv.lockout.entities.policy.nonlocking;

public interface NonLockingStateCalculatorMother {

    static NonLockingStateCalculator build() {
        return new NonLockingStateCalculator();
    }

}
