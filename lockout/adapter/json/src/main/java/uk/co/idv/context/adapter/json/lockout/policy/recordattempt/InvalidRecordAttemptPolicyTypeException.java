package uk.co.idv.context.adapter.json.lockout.policy.recordattempt;

public class InvalidRecordAttemptPolicyTypeException extends RuntimeException {

    public InvalidRecordAttemptPolicyTypeException(String type) {
        super(type);
    }

}
