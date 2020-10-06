package uk.co.idv.context.adapter.json.policy.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.policy.MethodPolicies;
import uk.co.idv.method.entities.policy.MethodPolicy;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class MethodPoliciesDeserializer extends StdDeserializer<MethodPolicies> {

    private static final TypeReference<Collection<MethodPolicy>> METHOD_POLICY_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    protected MethodPoliciesDeserializer() {
        super(MethodPolicies.class);
    }

    @Override
    public MethodPolicies deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new MethodPolicies(JsonNodeConverter.toCollection(node, parser, METHOD_POLICY_COLLECTION));
    }

}
