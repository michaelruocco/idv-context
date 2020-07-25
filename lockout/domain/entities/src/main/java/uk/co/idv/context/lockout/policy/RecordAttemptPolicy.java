package uk.co.idv.context.lockout.policy;

public interface RecordAttemptPolicy {

    String getType();

    boolean shouldRecordAttempt(RecordAttemptRequest request);

}
