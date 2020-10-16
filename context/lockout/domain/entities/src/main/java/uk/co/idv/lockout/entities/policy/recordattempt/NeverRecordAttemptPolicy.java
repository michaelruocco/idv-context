package uk.co.idv.lockout.entities.policy.recordattempt;

import lombok.Data;
import uk.co.idv.lockout.entities.policy.RecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.RecordAttemptRequest;

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
