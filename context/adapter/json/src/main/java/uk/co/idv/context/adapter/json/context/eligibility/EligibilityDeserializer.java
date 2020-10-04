package uk.co.idv.context.adapter.json.context.eligibility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.eligibility.AsyncEligibility;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.eligibility.Eligible;
import uk.co.idv.method.entities.eligibility.Ineligible;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class EligibilityDeserializer extends StdDeserializer<Eligibility> {

    protected EligibilityDeserializer() {
        super(Eligibility.class);
    }

    @Override
    public Eligibility deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        if (node.has("complete")) {
            return JsonNodeConverter.toObject(node, parser, AsyncEligibility.class);
        }
        if (node.get("eligible").asBoolean()) {
            return new Eligible();
        }
        return new Ineligible(node.get("reason").asText());
    }

}
