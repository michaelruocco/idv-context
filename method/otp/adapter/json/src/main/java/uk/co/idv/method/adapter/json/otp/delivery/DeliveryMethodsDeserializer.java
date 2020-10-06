package uk.co.idv.method.adapter.json.otp.delivery;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

class DeliveryMethodsDeserializer extends StdDeserializer<DeliveryMethods> {

    private static final TypeReference<Collection<DeliveryMethod>> DELIVERY_METHOD_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    protected DeliveryMethodsDeserializer() {
        super(DeliveryMethods.class);
    }

    @Override
    public DeliveryMethods deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new DeliveryMethods(JsonNodeConverter.toCollection(node, parser, DELIVERY_METHOD_COLLECTION));
    }

}
