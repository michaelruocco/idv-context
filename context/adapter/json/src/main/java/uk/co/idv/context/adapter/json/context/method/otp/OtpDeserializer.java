package uk.co.idv.context.adapter.json.context.method.otp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.policy.method.otp.OtpConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class OtpDeserializer extends StdDeserializer<Otp> {

    protected OtpDeserializer() {
        super(Otp.class);
    }

    @Override
    public Otp deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Otp.builder()
                .name(node.get("name").asText())
                .otpConfig(JsonNodeConverter.toObject(node.get("config"), parser, OtpConfig.class))
                .deliveryMethods(JsonNodeConverter.toObject(node.get("deliveryMethods"), parser, DeliveryMethods.class))
                .build();
    }

}
