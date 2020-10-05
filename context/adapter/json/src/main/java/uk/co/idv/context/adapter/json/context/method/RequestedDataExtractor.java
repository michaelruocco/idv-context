package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.mruoc.json.jackson.JsonNodeConverter;

import java.util.Optional;

public interface RequestedDataExtractor {

    static RequestedData extractRequestedData(JsonNode node, JsonParser parser) {
        return Optional.ofNullable(node.get("requestedData"))
                .map(dataNode -> JsonNodeConverter.toObject(dataNode, parser, RequestedData.class))
                .orElse(new RequestedData());
    }

}
