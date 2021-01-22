package uk.co.idv.lockout.adapter.json.policy.includeattempt;

public class InvalidIncludeAttemptPolicyTypeException extends RuntimeException {

    public InvalidIncludeAttemptPolicyTypeException(String type) {
        super(type);
    }

}
