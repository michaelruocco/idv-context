package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.method.otp.delivery.DeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.email.EmailDeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.sms.SmsDeliveryMethodConfig;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.voice.VoiceDeliveryMethodConfig;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Map;
import java.util.Optional;

public class DeliveryMethodConfigDeserializer extends StdDeserializer<DeliveryMethodConfig> {

    private final Map<String, Class<? extends DeliveryMethodConfig>> mappings;

    protected DeliveryMethodConfigDeserializer() {
        this(buildMappings());
    }

    public DeliveryMethodConfigDeserializer(Map<String, Class<? extends DeliveryMethodConfig>> mappings) {
        super(Alias.class);
        this.mappings = mappings;
    }

    @Override
    public DeliveryMethodConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String type = extractType(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(type));
    }

    private static String extractType(JsonNode node) {
        return node.get("type").asText();
    }

    private Class<? extends DeliveryMethodConfig> toMappingType(String type) {
        return Optional.ofNullable(mappings.get(type))
                .orElseThrow(() -> new InvalidDeliveryMethodConfigTypeException(type));
    }

    private static Map<String, Class<? extends DeliveryMethodConfig>> buildMappings() {
        return Map.of(
                SmsDeliveryMethodConfig.TYPE, SmsDeliveryMethodConfig.class,
                VoiceDeliveryMethodConfig.TYPE, VoiceDeliveryMethodConfig.class,
                EmailDeliveryMethodConfig.TYPE, EmailDeliveryMethodConfig.class
        );
    }

}
