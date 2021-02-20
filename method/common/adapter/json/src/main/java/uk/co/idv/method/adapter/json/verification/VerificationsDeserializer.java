package uk.co.idv.method.adapter.json.verification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.verification.Verification;
import uk.co.idv.method.entities.verification.Verifications;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class VerificationsDeserializer extends StdDeserializer<Verifications> {

    private static final TypeReference<Collection<Verification>> VERIFICATION_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    protected VerificationsDeserializer() {
        super(Verifications.class);
    }

    @Override
    public Verifications deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new Verifications(JsonNodeConverter.toCollection(node, parser, VERIFICATION_COLLECTION));
    }

}
