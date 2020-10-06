package uk.co.idv.method.adapter.json.otp.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.otp.OtpConfig;
import uk.co.idv.method.entities.otp.PasscodeConfig;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Duration;

public class OtpConfigDeserializer extends StdDeserializer<OtpConfig> {

    protected OtpConfigDeserializer() {
        super(OtpConfig.class);
    }

    @Override
    public OtpConfig deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return OtpConfig.builder()
                .maxNumberOfAttempts(node.get("maxNumberOfAttempts").asInt())
                .duration(JsonNodeConverter.toObject(node.get("duration"), parser, Duration.class))
                .passcodeConfig(JsonNodeConverter.toObject(node.get("passcodeConfig"), parser, PasscodeConfig.class))
                .build();
    }

}
