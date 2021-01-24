package uk.co.idv.context.adapter.json.context.sequence.stage;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.stage.Stage;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class StageDeserializer extends StdDeserializer<Stage> {

    protected StageDeserializer() {
        super(Stage.class);
    }

    @Override
    public Stage deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Stage.builder()
                .type(JsonNodeConverter.toObject(node, parser, StageType.class))
                .methods(JsonNodeConverter.toObject(node.get("methods"), parser, Methods.class))
                .build();
    }

}
