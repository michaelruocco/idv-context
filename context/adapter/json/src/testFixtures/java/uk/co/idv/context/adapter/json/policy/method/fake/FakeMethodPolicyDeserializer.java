package uk.co.idv.context.adapter.json.policy.method.fake;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.context.method.RequestedDataExtractor;
import uk.co.idv.method.entities.policy.FakeMethodConfig;
import uk.co.idv.method.entities.policy.FakeMethodPolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class FakeMethodPolicyDeserializer extends StdDeserializer<FakeMethodPolicy> {

    protected FakeMethodPolicyDeserializer() {
        super(FakeMethodPolicy.class);
    }

    @Override
    public FakeMethodPolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return FakeMethodPolicy.builder()
                .name(node.get("name").asText())
                .config(JsonNodeConverter.toObject(node.get("config"), parser, FakeMethodConfig.class))
                .requestedData(RequestedDataExtractor.extractRequestedData(node, parser))
                .build();
    }

}
