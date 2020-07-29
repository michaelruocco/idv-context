package uk.co.idv.context.adapter.json.alias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.ArrayList;
import java.util.Collection;

public class AliasesDeserializer extends StdDeserializer<Aliases> {

    public AliasesDeserializer() {
        super(Aliases.class);
    }

    @Override
    public Aliases deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        Collection<Alias> aliases = new ArrayList<>();
        for (JsonNode arrayNode : node) {
            aliases.add(JsonNodeConverter.toObject(arrayNode, parser, Alias.class));
        }
        return new Aliases(aliases);
    }

}
