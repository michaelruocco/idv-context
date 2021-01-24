package uk.co.idv.context.adapter.json.policy.sequence.stage;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicies;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class StagePoliciesDeserializer extends StdDeserializer<StagePolicies> {

    private static final TypeReference<Collection<StagePolicy>> STAGE_POLICY_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    protected StagePoliciesDeserializer() {
        super(StagePolicies.class);
    }

    @Override
    public StagePolicies deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new StagePolicies(JsonNodeConverter.toCollection(node, parser, STAGE_POLICY_COLLECTION));
    }

}
