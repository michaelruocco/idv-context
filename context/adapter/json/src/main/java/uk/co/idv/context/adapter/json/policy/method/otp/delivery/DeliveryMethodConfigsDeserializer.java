package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfigs;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class DeliveryMethodConfigsDeserializer extends StdDeserializer<DeliveryMethodConfigs> {

    private static final TypeReference<Collection<DeliveryMethodConfig>> METHOD_CONFIG_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    protected DeliveryMethodConfigsDeserializer() {
        super(DeliveryMethodConfigs.class);
    }

    @Override
    public DeliveryMethodConfigs deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new DeliveryMethodConfigs(JsonNodeConverter.toCollection(node, parser, METHOD_CONFIG_COLLECTION));
    }

}
