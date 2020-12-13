package uk.co.idv.method.adapter.json.otp.policy.delivery.phone;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.method.entities.otp.policy.delivery.LastUpdatedConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.OtpPhoneNumberConfig;
import uk.co.idv.method.entities.otp.policy.delivery.phone.SimSwapConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Optional;

public class OtpPhoneNumberConfigDeserializer extends StdDeserializer<OtpPhoneNumberConfig> {

    public OtpPhoneNumberConfigDeserializer() {
        super(OtpPhoneNumberConfig.class);
    }

    @Override
    public OtpPhoneNumberConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return OtpPhoneNumberConfig.builder()
                .country(CountryCode.valueOf(node.get("country").asText()))
                .allowInternational(node.get("allowInternational").asBoolean())
                .lastUpdatedConfig(JsonNodeConverter.toObject(node.get("lastUpdatedConfig"), parser, LastUpdatedConfig.class))
                .simSwapConfig(toSimSwapConfigIfPresent(node, parser))
                .build();
    }

    private SimSwapConfig toSimSwapConfigIfPresent(JsonNode node, JsonParser parser) {
        return Optional.ofNullable(node.get("simSwapConfig"))
                .map(configNode -> JsonNodeConverter.toObject(configNode, parser, SimSwapConfig.class))
                .orElse(null);
    }

}
