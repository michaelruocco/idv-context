package uk.co.idv.context.entities.lockout.policy;

public interface RecordAttemptPolicy {

    String getType();

    boolean shouldRecordAttempt(RecordAttemptRequest request);

}
