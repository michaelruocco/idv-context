package uk.co.idv.lockout.adapter.json.policy.recordattempt;

public class InvalidRecordAttemptPolicyTypeException extends RuntimeException {

    public InvalidRecordAttemptPolicyTypeException(String type) {
        super(type);
    }

}
