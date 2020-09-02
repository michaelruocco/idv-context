
package uk.co.idv.identity.adapter.json.eligibility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequest;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import static uk.co.idv.identity.adapter.json.eligibility.EligibilityFieldExtractor.toAliases;
import static uk.co.idv.identity.adapter.json.eligibility.EligibilityFieldExtractor.toChannel;
import static uk.co.idv.identity.adapter.json.eligibility.EligibilityFieldExtractor.toRequestedData;

public class CreateEligibilityRequestDeserializer extends StdDeserializer<CreateEligibilityRequest> {

    protected CreateEligibilityRequestDeserializer() {
        super(CreateEligibilityRequest.class);
    }

    @Override
    public CreateEligibilityRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return CreateEligibilityRequest.builder()
                .channel(toChannel(node, parser))
                .aliases(toAliases(node, parser))
                .requestedData(toRequestedData(node, parser))
                .build();
    }

}
