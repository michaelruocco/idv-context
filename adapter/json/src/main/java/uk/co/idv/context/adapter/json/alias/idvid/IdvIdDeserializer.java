package uk.co.idv.context.adapter.json.alias.idvid;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.alias.IdvId;

public class IdvIdDeserializer extends StdDeserializer<IdvId> {

    public IdvIdDeserializer() {
        super(IdvId.class);
    }

    @Override
    public IdvId deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new IdvId(node.get("value").asText());
    }

}
