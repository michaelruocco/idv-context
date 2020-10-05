package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.simswap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.otp.policy.delivery.phone.AcceptableSimSwapStatuses;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class AcceptableSimSwapStatusesDeserializer extends StdDeserializer<AcceptableSimSwapStatuses> {

    public AcceptableSimSwapStatusesDeserializer() {
        super(AcceptableSimSwapStatuses.class);
    }

    @Override
    public AcceptableSimSwapStatuses deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        Collection<String> values = JsonNodeConverter.toStringCollection(node, parser);
        return new AcceptableSimSwapStatuses(values);
    }

}
