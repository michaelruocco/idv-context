package uk.co.idv.context.adapter.json.alias.defaultalias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.alias.DefaultAlias;

public class DefaultAliasDeserializer extends StdDeserializer<DefaultAlias> {

    public DefaultAliasDeserializer() {
        super(DefaultAlias.class);
    }

    @Override
    public DefaultAlias deserialize(JsonParser parser, DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultAlias.builder()
                .type(node.get("type").asText())
                .value(node.get("value").asText())
                .build();
    }

}
