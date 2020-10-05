package uk.co.idv.context.adapter.json.context.method.fake;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.context.method.ResultsExtractor;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.fake.FakeMethod;
import uk.co.idv.method.entities.fake.policy.FakeMethodConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class FakeMethodDeserializer extends StdDeserializer<FakeMethod> {

    protected FakeMethodDeserializer() {
        super(FakeMethod.class);
    }

    @Override
    public FakeMethod deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return FakeMethod.builder()
                .name(node.get("name").asText())
                .config(JsonNodeConverter.toObject(node.get("config"), parser, FakeMethodConfig.class))
                .eligibility(JsonNodeConverter.toObject(node.get("eligibility"), parser, Eligibility.class))
                .results(ResultsExtractor.extractResults(node, parser))
                .build();
    }

}
