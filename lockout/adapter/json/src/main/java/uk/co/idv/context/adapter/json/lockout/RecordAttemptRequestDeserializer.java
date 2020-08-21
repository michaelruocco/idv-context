package uk.co.idv.context.adapter.json.lockout;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.lockout.DefaultRecordAttemptRequest;
import uk.co.idv.context.entities.lockout.attempt.Attempt;
import uk.co.idv.context.entities.lockout.policy.RecordAttemptRequest;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class RecordAttemptRequestDeserializer extends StdDeserializer<RecordAttemptRequest> {

    public RecordAttemptRequestDeserializer() {
        super(RecordAttemptRequest.class);
    }

    @Override
    public RecordAttemptRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultRecordAttemptRequest.builder()
                .sequenceComplete(node.get("sequenceComplete").asBoolean())
                .methodComplete(node.get("methodComplete").asBoolean())
                .attempt(JsonNodeConverter.toObject(node.get("attempt"), parser, Attempt.class))
                .build();
    }

}
