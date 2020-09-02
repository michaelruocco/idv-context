package uk.co.idv.lockout.entities.policy.hard;


public interface HardLockoutStateCalculatorMother {

    static HardLockoutStateCalculator build() {
        return withMaxNumberOfAttempts(3);
    }

    static HardLockoutStateCalculator withMaxNumberOfAttempts(int maxNumberOfAttempts) {
         return new HardLockoutStateCalculator(maxNumberOfAttempts);
    }

}
