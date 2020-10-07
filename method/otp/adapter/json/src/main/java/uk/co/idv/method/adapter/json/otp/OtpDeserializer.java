package uk.co.idv.method.adapter.json.otp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
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
                .config(JsonNodeConverter.toObject(node.get("config"), parser, OtpConfig.class))
                .deliveryMethods(JsonNodeConverter.toObject(node.get("deliveryMethods"), parser, DeliveryMethods.class))
                .build();
    }

}
