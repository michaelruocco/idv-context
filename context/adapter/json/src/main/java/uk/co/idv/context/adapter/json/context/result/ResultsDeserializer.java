package uk.co.idv.context.adapter.json.context.result;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.Results;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class ResultsDeserializer extends StdDeserializer<Results> {

    private static final TypeReference<Collection<Result>> RESULT_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    protected ResultsDeserializer() {
        super(Results.class);
    }

    @Override
    public Results deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new Results(JsonNodeConverter.toCollection(node, parser, RESULT_COLLECTION));
    }

}
