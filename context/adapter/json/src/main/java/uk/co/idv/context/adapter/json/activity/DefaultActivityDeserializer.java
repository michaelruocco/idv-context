package uk.co.idv.context.adapter.json.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.activity.DefaultActivity;
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
                .timestamp(Instant.parse(node.get("timestamp").asText()))
                .build();
    }

}
