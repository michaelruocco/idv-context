package uk.co.idv.context.adapter.json.alias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.DefaultAliases;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class AliasesDeserializer extends StdDeserializer<Aliases> {

    private static final TypeReference<Collection<Alias>> ALIAS_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    public AliasesDeserializer() {
        super(DefaultAliases.class);
    }

    @Override
    public Aliases deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new DefaultAliases(JsonNodeConverter.toCollection(node, parser, ALIAS_COLLECTION));
    }

}
