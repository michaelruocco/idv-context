package uk.co.idv.identity.adapter.json.alias.defaultalias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.alias.DefaultAlias;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class DefaultAliasDeserializer extends StdDeserializer<DefaultAlias> {

    public DefaultAliasDeserializer() {
        super(DefaultAlias.class);
    }

    @Override
    public DefaultAlias deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultAlias.builder()
                .type(node.get("type").asText())
                .value(node.get("value").asText())
                .build();
    }

}
