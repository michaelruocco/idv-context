

package uk.co.idv.lockout.entities.policy.recordattempt;

import lombok.Data;
import uk.co.idv.lockout.entities.policy.RecordAttemptPolicy;
import uk.co.idv.lockout.entities.policy.RecordAttemptRequest;

@Data
public class RecordAttemptWhenSequenceCompletePolicy implements RecordAttemptPolicy {

    public static final String TYPE = "record-when-sequence-complete";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean shouldRecordAttempt(RecordAttemptRequest request) {
        return request.isSequenceComplete();
    }

}
