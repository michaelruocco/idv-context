package uk.co.idv.activity.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.activity.entities.Login;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;

class LoginDeserializer extends StdDeserializer<Login> {

    protected LoginDeserializer() {
        super(Login.class);
    }

    @Override
    public Login deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Login.builder()
                .system(node.get("system").asText())
                .timestamp(JsonNodeConverter.toObject(node.get("timestamp"), parser, Instant.class))
                .build();
    }

}
