package uk.co.idv.context.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collection;

public class JsonNodeConverter {

    private JsonNodeConverter() {
        // utility class
    }

    public static Collection<String> toStringCollection(JsonNode node, JsonParser parser) {
        return Arrays.asList(toObject(node, parser, String[].class));
    }

    public static <T> T toObject(JsonNode node, JsonParser parser, Class<T> type) {
        try {
            return node.traverse(parser.getCodec()).readValueAs(type);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
