package uk.co.idv.context.adapter.json.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.activity.Login;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;

class LoginDeserializer extends StdDeserializer<Login> {

    protected LoginDeserializer() {
        super(Login.class);
    }

    @Override
    public Login deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new Login(Instant.parse(node.get("timestamp").asText()));
    }

}
