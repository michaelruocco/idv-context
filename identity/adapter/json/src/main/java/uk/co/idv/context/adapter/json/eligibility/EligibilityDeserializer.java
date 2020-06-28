package uk.co.idv.context.adapter.json.eligibility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonNodeConverter;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.eligibility.Eligibility;
import uk.co.idv.context.entities.identity.Identity;


import static uk.co.idv.context.adapter.json.eligibility.EligibilityFieldExtractor.toAliases;
import static uk.co.idv.context.adapter.json.eligibility.EligibilityFieldExtractor.toChannel;
import static uk.co.idv.context.adapter.json.eligibility.EligibilityFieldExtractor.toRequested;

public class EligibilityDeserializer extends StdDeserializer<Eligibility> {

    protected EligibilityDeserializer() {
        super(Eligibility.class);
    }

    @Override
    public Eligibility deserialize(JsonParser parser, DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return Eligibility.builder()
                .channel(toChannel(node, parser))
                .aliases(toAliases(node, parser))
                .requested(toRequested(node, parser))
                .identity(toIdentity(node, parser))
                .build();
    }

    private static Identity toIdentity(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("identity"), parser, Identity.class);
    }

}