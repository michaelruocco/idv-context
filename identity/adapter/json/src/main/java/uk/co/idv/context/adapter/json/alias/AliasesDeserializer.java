package uk.co.idv.context.adapter.json.alias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonNodeConverter;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;

import java.util.ArrayList;
import java.util.Collection;

public class AliasesDeserializer extends StdDeserializer<Aliases> {

    public AliasesDeserializer() {
        super(Aliases.class);
    }

    @Override
    public Aliases deserialize(JsonParser parser, DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final Collection<Alias> aliases = new ArrayList<>();
        for (JsonNode arrayNode : node) {
            aliases.add(JsonNodeConverter.toObject(arrayNode, parser, Alias.class));
        }
        return new Aliases(aliases);
    }

}
