package uk.co.idv.lockout.adapter.json.policy.recordattempt.sequencecomplete;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.recordattempt.RecordAttemptWhenSequenceCompletePolicy;

public class RecordAttemptWhenSequenceCompletePolicyDeserializer extends StdDeserializer<RecordAttemptWhenSequenceCompletePolicy> {

    public RecordAttemptWhenSequenceCompletePolicyDeserializer() {
        super(RecordAttemptWhenSequenceCompletePolicy.class);
    }

    @Override
    public RecordAttemptWhenSequenceCompletePolicy deserialize(JsonParser parser, DeserializationContext context) {
        return new RecordAttemptWhenSequenceCompletePolicy();
    }

}
