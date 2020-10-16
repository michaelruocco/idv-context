package uk.co.idv.lockout.entities.policy;

public interface LockoutStateCalculator {

    String getType();

    LockoutState calculate(LockoutStateRequest request);

}
