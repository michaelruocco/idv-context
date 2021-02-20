package uk.co.idv.context.adapter.json.policy.sequence.stage;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicy;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;
import uk.co.idv.method.entities.policy.MethodPolicies;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class StagePolicyDeserializer extends StdDeserializer<StagePolicy> {

    protected StagePolicyDeserializer() {
        super(StagePolicy.class);
    }

    @Override
    public StagePolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return StagePolicy.builder()
                .type(JsonNodeConverter.toObject(node, parser, StageType.class))
                .methodPolicies(JsonNodeConverter.toObject(node.get("methodPolicies"), parser, MethodPolicies.class))
                .build();
    }

}
