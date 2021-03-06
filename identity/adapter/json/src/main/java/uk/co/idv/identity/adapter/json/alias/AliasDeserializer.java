package uk.co.idv.identity.adapter.json.alias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.CardNumber;
import uk.co.idv.identity.entities.alias.DefaultAlias;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

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
        JsonNode node = JsonParserConverter.toNode(parser);
        String type = extractType(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(type));
    }

    private static String extractType(JsonNode node) {
        return node.get("type").asText();
    }

    private Class<? extends Alias> toMappingType(String type) {
        return mappings.getOrDefault(type, DefaultAlias.class);
    }

    private static Map<String, Class<? extends Alias>> buildMappings() {
        return Map.of(
                IdvId.TYPE, IdvId.class,
                CardNumber.CREDIT_TYPE, CardNumber.class,
                CardNumber.DEBIT_TYPE, CardNumber.class
        );
    }

}
