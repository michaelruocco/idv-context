package uk.co.idv.context.adapter.json.lockout.policy.recordattempt.sequencecomplete;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.lockout.policy.recordattempt.RecordAttemptWhenSequenceCompletePolicy;

public class RecordAttemptWhenSequenceCompletePolicyDeserializer extends StdDeserializer<RecordAttemptWhenSequenceCompletePolicy> {

    public RecordAttemptWhenSequenceCompletePolicyDeserializer() {
        super(RecordAttemptWhenSequenceCompletePolicy.class);
    }

    @Override
    public RecordAttemptWhenSequenceCompletePolicy deserialize(JsonParser parser, DeserializationContext context) {
        return new RecordAttemptWhenSequenceCompletePolicy();
    }

}
