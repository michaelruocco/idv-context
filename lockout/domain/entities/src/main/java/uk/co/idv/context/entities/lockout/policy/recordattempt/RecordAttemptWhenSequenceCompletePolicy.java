

package uk.co.idv.context.entities.lockout.policy.recordattempt;

import lombok.Data;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptPolicy;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;

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
