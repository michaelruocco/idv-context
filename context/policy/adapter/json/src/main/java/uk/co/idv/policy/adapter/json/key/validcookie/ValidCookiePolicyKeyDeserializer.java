package uk.co.idv.policy.adapter.json.key.validcookie;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.policy.entities.policy.key.channelactivity.ChannelActivityPolicyKey;
import uk.co.idv.policy.entities.policy.key.validcookie.ValidCookiePolicyKey;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class ValidCookiePolicyKeyDeserializer extends StdDeserializer<ValidCookiePolicyKey> {

    public ValidCookiePolicyKeyDeserializer() {
        super(ValidCookiePolicyKey.class);
    }

    @Override
    public ValidCookiePolicyKey deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return ValidCookiePolicyKey.builder()
                .baseKey(JsonNodeConverter.toObject(node, parser, ChannelActivityPolicyKey.class))
                .validCookie(node.get("validCookie").asBoolean())
                .build();
    }

}
