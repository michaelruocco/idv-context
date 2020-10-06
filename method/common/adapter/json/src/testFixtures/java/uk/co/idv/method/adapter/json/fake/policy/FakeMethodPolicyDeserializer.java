package uk.co.idv.method.adapter.json.fake.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.adapter.json.policy.RequestedDataExtractor;
import uk.co.idv.method.entities.method.fake.FakeMethodConfig;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicy;
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
