package uk.co.idv.context.adapter.json.context.method.fake;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.fake.FakeMethodConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Duration;

class FakeMethodConfigDeserializer extends StdDeserializer<FakeMethodConfig> {

    protected FakeMethodConfigDeserializer() {
        super(FakeMethodConfig.class);
    }

    @Override
    public FakeMethodConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return FakeMethodConfig.builder()
                .duration(JsonNodeConverter.toObject(node.get("duration"), parser, Duration.class))
                .maxNumberOfAttempts(node.get("maxNumberOfAttempts").asInt())
                .fakeValue(node.get("fakeValue").asText())
                .build();
    }

}
