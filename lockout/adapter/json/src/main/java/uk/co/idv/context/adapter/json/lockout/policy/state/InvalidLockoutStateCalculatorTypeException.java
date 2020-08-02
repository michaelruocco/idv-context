package uk.co.idv.context.adapter.json.lockout.policy.state;

public class InvalidLockoutStateCalculatorTypeException extends RuntimeException {

    public InvalidLockoutStateCalculatorTypeException(String type) {
        super(type);
    }

}
