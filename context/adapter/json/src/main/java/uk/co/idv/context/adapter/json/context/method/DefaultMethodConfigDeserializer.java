package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.method.DefaultMethodConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Duration;

public class DefaultMethodConfigDeserializer extends StdDeserializer<DefaultMethodConfig> {

    public DefaultMethodConfigDeserializer() {
        super(DefaultMethodConfig.class);
    }

    @Override
    public DefaultMethodConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultMethodConfig.builder()
                .duration(JsonNodeConverter.toObject(node.get("duration"), parser, Duration.class))
                .maxNumberOfAttempts(node.get("maxNumberOfAttempts").asInt())
                .build();
    }

}
