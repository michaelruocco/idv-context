package uk.co.idv.context.adapter.json.policy.sequence.stage.type;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.policy.sequence.stage.AllMethodsStageType;
import uk.co.idv.context.entities.policy.sequence.stage.AnyMethodStageType;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class StageTypeDeserializer extends StdDeserializer<StageType> {

    public StageTypeDeserializer() {
        super(StageType.class);
    }

    @Override
    public StageType deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String type = node.get("type").asText();
        switch (type) {
            case AllMethodsStageType.NAME: return new AllMethodsStageType();
            case AnyMethodStageType.NAME: return new AnyMethodStageType();
            default: throw new InvalidStageTypeException(type);
        }
    }

}
