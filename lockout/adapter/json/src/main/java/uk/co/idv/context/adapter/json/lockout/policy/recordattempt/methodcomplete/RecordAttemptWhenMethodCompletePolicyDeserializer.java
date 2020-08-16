package uk.co.idv.context.adapter.json.lockout.policy.recordattempt.methodcomplete;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.lockout.policy.recordattempt.RecordAttemptWhenMethodCompletePolicy;

public class RecordAttemptWhenMethodCompletePolicyDeserializer extends StdDeserializer<RecordAttemptWhenMethodCompletePolicy> {

    public RecordAttemptWhenMethodCompletePolicyDeserializer() {
        super(RecordAttemptWhenMethodCompletePolicy.class);
    }

    @Override
    public RecordAttemptWhenMethodCompletePolicy deserialize(JsonParser parser, DeserializationContext context) {
        return new RecordAttemptWhenMethodCompletePolicy();
    }

}
