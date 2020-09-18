package uk.co.idv.context.adapter.json.policy.method.otp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.method.otp.PasscodeConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Duration;

public class PasscodeConfigDeserializer extends StdDeserializer<PasscodeConfig> {

    protected PasscodeConfigDeserializer() {
        super(PasscodeConfig.class);
    }

    @Override
    public PasscodeConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return PasscodeConfig.builder()
                .length(node.get("length").asInt())
                .duration(JsonNodeConverter.toObject(node.get("duration"), parser, Duration.class))
                .maxNumberOfDeliveries(node.get("maxNumberOfDeliveries").asInt())
                .build();
    }

}
