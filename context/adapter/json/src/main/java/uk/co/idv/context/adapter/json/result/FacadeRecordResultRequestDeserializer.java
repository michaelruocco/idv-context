package uk.co.idv.context.adapter.json.result;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.method.entities.result.Result;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.UUID;

class FacadeRecordResultRequestDeserializer extends StdDeserializer<FacadeRecordResultRequest> {

    protected FacadeRecordResultRequestDeserializer() {
        super(FacadeRecordResultRequest.class);
    }

    @Override
    public FacadeRecordResultRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return FacadeRecordResultRequest.builder()
                .contextId(UUID.fromString(node.get("contextId").asText()))
                .result(JsonNodeConverter.toObject(node.get("result"), parser, Result.class))
                .build();
    }

}
