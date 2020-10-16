package uk.co.idv.lockout.adapter.json.policy.state;

public class InvalidLockoutStateCalculatorTypeException extends RuntimeException {

    public InvalidLockoutStateCalculatorTypeException(String type) {
        super(type);
    }

}
