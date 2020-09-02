package uk.co.idv.lockout.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.lockout.entities.DefaultRecordAttemptRequest;
import uk.co.idv.lockout.entities.attempt.Attempt;
import uk.co.idv.lockout.entities.policy.RecordAttemptRequest;
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
