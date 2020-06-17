package uk.co.idv.context.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.UncheckedIOException;

public class JsonNodeConverter {

    private JsonNodeConverter() {
        // utility class
    }

    public static <T> T toObject(final JsonNode node, final JsonParser parser, final Class<T> type) {
        try {
            return node.traverse(parser.getCodec()).readValueAs(type);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
