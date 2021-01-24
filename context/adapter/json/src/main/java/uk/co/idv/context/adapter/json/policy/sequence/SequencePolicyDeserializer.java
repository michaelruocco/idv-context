package uk.co.idv.context.adapter.json.policy.sequence;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicies;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class SequencePolicyDeserializer extends StdDeserializer<SequencePolicy> {

    protected SequencePolicyDeserializer() {
        super(SequencePolicy.class);
    }

    @Override
    public SequencePolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return SequencePolicy.builder()
                .name(node.get("name").asText())
                .stagePolicies(JsonNodeConverter.toObject(node.get("stagePolicies"), parser, StagePolicies.class))
                .build();
    }

}
