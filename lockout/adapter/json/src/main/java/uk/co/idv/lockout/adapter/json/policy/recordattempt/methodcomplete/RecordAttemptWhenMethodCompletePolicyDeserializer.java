package uk.co.idv.lockout.adapter.json.policy.recordattempt.methodcomplete;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.policy.recordattempt.RecordAttemptWhenMethodCompletePolicy;

public class RecordAttemptWhenMethodCompletePolicyDeserializer extends StdDeserializer<RecordAttemptWhenMethodCompletePolicy> {

    public RecordAttemptWhenMethodCompletePolicyDeserializer() {
        super(RecordAttemptWhenMethodCompletePolicy.class);
    }

    @Override
    public RecordAttemptWhenMethodCompletePolicy deserialize(JsonParser parser, DeserializationContext context) {
        return new RecordAttemptWhenMethodCompletePolicy();
    }

}
