package uk.co.idv.context.lockout.policy.recordattempt;

import uk.co.idv.context.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.lockout.policy.RecordAttemptRequest;

public class NeverRecordAttempts implements RecordAttemptPolicy {

    @Override
    public String getType() {
        return "never-record";
    }

    @Override
    public boolean shouldRecordAttempt(RecordAttemptRequest request) {
        return false;
    }

}
