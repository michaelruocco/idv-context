package uk.co.idv.context.entities.lockout.policy.recordattempt;

import uk.co.idv.context.entities.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

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
