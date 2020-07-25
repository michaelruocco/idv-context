package uk.co.idv.context.lockout.policy;

public interface LockoutStateCalculator {

    String getType();

    LockoutState calculate(LockoutStateRequest request);

}
