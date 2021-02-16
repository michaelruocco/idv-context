package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.entities.method.Method;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class MethodsDeserializer extends StdDeserializer<Methods> {

    private static final TypeReference<Collection<Method>> METHOD_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    protected MethodsDeserializer() {
        super(Methods.class);
    }

    @Override
    public Methods deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new Methods(JsonNodeConverter.toCollection(node, parser, METHOD_COLLECTION));
    }

}
