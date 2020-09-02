package uk.co.idv.identity.adapter.json.alias.idvid;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class IdvIdDeserializer extends StdDeserializer<IdvId> {

    public IdvIdDeserializer() {
        super(IdvId.class);
    }

    @Override
    public IdvId deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new IdvId(node.get("value").asText());
    }

}
