package uk.co.idv.method.adapter.json.result;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.method.entities.result.Results;
import uk.co.mruoc.json.jackson.JsonNodeConverter;

import java.util.Optional;

public interface ResultsExtractor {

    static Results extractResults(JsonNode node, JsonParser parser) {
        return Optional.ofNullable(node.get("results"))
                .map(dataNode -> JsonNodeConverter.toObject(dataNode, parser, Results.class))
                .orElse(new Results());
    }

}
