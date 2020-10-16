package uk.co.idv.lockout.entities.policy;

public interface RecordAttemptPolicy {

    String getType();

    boolean shouldRecordAttempt(RecordAttemptRequest request);

}
