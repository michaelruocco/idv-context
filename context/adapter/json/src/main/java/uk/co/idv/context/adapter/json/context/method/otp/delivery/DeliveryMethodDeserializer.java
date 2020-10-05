package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.UUID;

class DeliveryMethodDeserializer extends StdDeserializer<DeliveryMethod> {

    protected DeliveryMethodDeserializer() {
        super(DeliveryMethod.class);
    }

    @Override
    public DeliveryMethod deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DeliveryMethod.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .type(node.get("type").asText())
                .value(node.get("value").asText())
                .eligibility(JsonNodeConverter.toObject(node.get("eligibility"), parser, Eligibility.class))
                .build();
    }

}
