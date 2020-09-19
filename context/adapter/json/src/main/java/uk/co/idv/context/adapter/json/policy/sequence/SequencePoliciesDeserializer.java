package uk.co.idv.context.adapter.json.policy.sequence;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class SequencePoliciesDeserializer extends StdDeserializer<SequencePolicies> {

    private static final TypeReference<Collection<SequencePolicy>> SEQUENCE_POLICY_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    protected SequencePoliciesDeserializer() {
        super(SequencePolicies.class);
    }

    @Override
    public SequencePolicies deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new SequencePolicies(JsonNodeConverter.toCollection(node, parser, SEQUENCE_POLICY_COLLECTION));
    }

}
