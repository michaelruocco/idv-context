package uk.co.idv.context.adapter.json.alias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonNodeConverter;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.CreditCardNumber;
import uk.co.idv.context.entities.alias.DebitCardNumber;
import uk.co.idv.context.entities.alias.DefaultAlias;
import uk.co.idv.context.entities.alias.IdvId;

import java.util.Map;

public class AliasDeserializer extends StdDeserializer<Alias> {

    private final Map<String, Class<? extends Alias>> mappings;

    protected AliasDeserializer() {
        this(buildMappings());
    }

    public AliasDeserializer(Map<String, Class<? extends Alias>> mappings) {
        super(Alias.class);
        this.mappings = mappings;
    }

    @Override
    public Alias deserialize(JsonParser parser, DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String type = extractType(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(type));
    }

    private static String extractType(JsonNode node) {
        return node.get("type").asText();
    }

    private Class<? extends Alias> toMappingType(String name) {
        return mappings.getOrDefault(name, DefaultAlias.class);
    }

    private static Map<String, Class<? extends Alias>> buildMappings() {
        return Map.of(
                IdvId.TYPE, IdvId.class,
                CreditCardNumber.TYPE, CreditCardNumber.class,
                DebitCardNumber.TYPE, DebitCardNumber.class
        );
    }

}
