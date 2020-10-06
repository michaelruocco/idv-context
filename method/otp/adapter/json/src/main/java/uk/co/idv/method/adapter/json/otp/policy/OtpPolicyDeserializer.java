package uk.co.idv.method.adapter.json.otp.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.method.entities.otp.policy.OtpPolicy;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigs;
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
                .config(JsonNodeConverter.toObject(node.get("config"), parser, OtpConfig.class))
                .deliveryMethodConfigs(JsonNodeConverter.toObject(node.get("deliveryMethodConfigs"), parser, DeliveryMethodConfigs.class))
                .build();
    }

}
