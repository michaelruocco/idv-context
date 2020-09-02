package uk.co.idv.context.adapter.json.identity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class RequestedDataDeserializer extends StdDeserializer<RequestedData> {

    public RequestedDataDeserializer() {
        super(RequestedData.class);
    }

    @Override
    public RequestedData deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new RequestedData(JsonNodeConverter.toStringCollection(node, parser));
    }

}
