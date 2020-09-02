package uk.co.idv.identity.adapter.json.eligibility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.eligibility.Eligibility;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;


import static uk.co.idv.identity.adapter.json.eligibility.EligibilityFieldExtractor.toAliases;
import static uk.co.idv.identity.adapter.json.eligibility.EligibilityFieldExtractor.toChannel;
import static uk.co.idv.identity.adapter.json.eligibility.EligibilityFieldExtractor.toRequestedData;

public class EligibilityDeserializer extends StdDeserializer<Eligibility> {

    protected EligibilityDeserializer() {
        super(Eligibility.class);
    }

    @Override
    public Eligibility deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Eligibility.builder()
                .channel(toChannel(node, parser))
                .aliases(toAliases(node, parser))
                .requestedData(toRequestedData(node, parser))
                .identity(toIdentity(node, parser))
                .build();
    }

    private static Identity toIdentity(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("identity"), parser, Identity.class);
    }

}
