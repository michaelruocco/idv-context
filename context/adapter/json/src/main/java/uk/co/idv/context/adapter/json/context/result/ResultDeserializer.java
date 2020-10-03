package uk.co.idv.context.adapter.json.context.result;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.result.Result;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;
import java.util.UUID;

class ResultDeserializer extends StdDeserializer<Result> {

    protected ResultDeserializer() {
        super(Result.class);
    }

    @Override
    public Result deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Result.builder()
                .methodName(node.get("methodName").asText())
                .successful(node.get("successful").asBoolean())
                .verificationId(UUID.fromString(node.get("verificationId").asText()))
                .timestamp(JsonNodeConverter.toObject(node.get("timestamp"), parser, Instant.class))
                .build();
    }

}
