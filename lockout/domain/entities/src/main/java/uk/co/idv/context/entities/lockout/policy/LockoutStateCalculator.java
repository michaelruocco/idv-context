package uk.co.idv.context.entities.lockout.policy;

public interface LockoutStateCalculator {

    String getType();

    LockoutState calculate(LockoutStateRequest request);

}
