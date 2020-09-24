package uk.co.idv.context.adapter.json.context.eligibility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.eligibility.Eligibility;
import uk.co.idv.context.entities.context.eligibility.Eligible;
import uk.co.idv.context.entities.context.eligibility.Ineligible;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class EligibilityDeserializer extends StdDeserializer<Eligibility> {

    protected EligibilityDeserializer() {
        super(Eligibility.class);
    }

    @Override
    public Eligibility deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        if (node.get("eligible").asBoolean()) {
            return new Eligible();
        }
        return new Ineligible(node.get("reason").asText());
    }

}