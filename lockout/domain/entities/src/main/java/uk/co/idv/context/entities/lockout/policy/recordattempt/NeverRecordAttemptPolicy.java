package uk.co.idv.context.entities.lockout.policy.recordattempt;

import lombok.Data;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

@Data
public class NeverRecordAttemptPolicy implements RecordAttemptPolicy {

    public static final String TYPE = "never-record";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean shouldRecordAttempt(RecordAttemptRequest request) {
        return false;
    }

}
