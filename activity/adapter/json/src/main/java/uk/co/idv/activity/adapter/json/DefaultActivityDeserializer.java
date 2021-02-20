package uk.co.idv.activity.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.activity.entities.DefaultActivity;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;

class DefaultActivityDeserializer extends StdDeserializer<DefaultActivity> {

    protected DefaultActivityDeserializer() {
        super(DefaultActivity.class);
    }

    @Override
    public DefaultActivity deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultActivity.builder()
                .name(node.get("name").asText())
                .timestamp(JsonNodeConverter.toObject(node.get("timestamp"), parser, Instant.class))
                .build();
    }

}
