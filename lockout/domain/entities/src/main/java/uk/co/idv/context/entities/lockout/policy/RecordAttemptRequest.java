package uk.co.idv.context.entities.lockout.policy;

public interface RecordAttemptRequest {

    boolean isSequenceComplete();

    boolean isMethodComplete();

}
