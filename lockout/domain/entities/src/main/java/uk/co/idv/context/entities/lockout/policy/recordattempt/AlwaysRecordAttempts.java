

package uk.co.idv.context.entities.lockout.policy.recordattempt;

import uk.co.idv.context.entities.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

public class AlwaysRecordAttempts implements RecordAttemptPolicy {

    @Override
    public String getType() {
        return "always-record";
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return true;
    }

}
