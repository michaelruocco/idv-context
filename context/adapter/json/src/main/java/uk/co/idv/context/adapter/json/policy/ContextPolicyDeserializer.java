package uk.co.idv.context.adapter.json.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.method.entities.otp.PasscodeConfig;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class ContextPolicyDeserializer extends StdDeserializer<ContextPolicy> {

    protected ContextPolicyDeserializer() {
        super(PasscodeConfig.class);
    }

    @Override
    public ContextPolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return ContextPolicy.builder()
                .key(JsonNodeConverter.toObject(node.get("key"), parser, PolicyKey.class))
                .sequencePolicies(JsonNodeConverter.toObject(node.get("sequencePolicies"), parser, SequencePolicies.class))
                .build();
    }

}
