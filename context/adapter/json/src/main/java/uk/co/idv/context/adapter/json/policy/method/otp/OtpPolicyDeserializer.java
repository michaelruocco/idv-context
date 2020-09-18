package uk.co.idv.context.adapter.json.policy.method.otp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.method.otp.OtpConfig;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicy;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfigs;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class OtpPolicyDeserializer extends StdDeserializer<OtpPolicy> {

    protected OtpPolicyDeserializer() {
        super(OtpPolicy.class);
    }

    @Override
    public OtpPolicy deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return OtpPolicy.builder()
                .methodConfig(JsonNodeConverter.toObject(node.get("methodConfig"), parser, OtpConfig.class))
                .deliveryMethodConfigs(JsonNodeConverter.toObject(node.get("deliveryMethodConfigs"), parser, DeliveryMethodConfigs.class))
                .build();
    }

}
