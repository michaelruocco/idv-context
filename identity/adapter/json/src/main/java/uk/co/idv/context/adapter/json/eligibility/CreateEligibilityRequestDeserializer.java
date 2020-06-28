
package uk.co.idv.context.adapter.json.eligibility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest;

import static uk.co.idv.context.adapter.json.eligibility.EligibilityFieldExtractor.toAliases;
import static uk.co.idv.context.adapter.json.eligibility.EligibilityFieldExtractor.toChannel;
import static uk.co.idv.context.adapter.json.eligibility.EligibilityFieldExtractor.toRequested;

public class CreateEligibilityRequestDeserializer extends StdDeserializer<CreateEligibilityRequest> {

    protected CreateEligibilityRequestDeserializer() {
        super(CreateEligibilityRequest.class);
    }

    @Override
    public CreateEligibilityRequest deserialize(JsonParser parser, DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return CreateEligibilityRequest.builder()
                .channel(toChannel(node, parser))
                .aliases(toAliases(node, parser))
                .requested(toRequested(node, parser))
                .build();
    }

}
