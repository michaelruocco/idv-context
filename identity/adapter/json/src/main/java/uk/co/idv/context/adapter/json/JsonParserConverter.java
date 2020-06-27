package uk.co.idv.context.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.UncheckedIOException;

public class JsonParserConverter {

    private JsonParserConverter() {
        // utility class
    }

    public static JsonNode toNode(final JsonParser parser) {
        try {
            return parser.getCodec().readTree(parser);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
