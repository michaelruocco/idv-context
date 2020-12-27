package uk.co.idv.context.adapter.json.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.NextMethods;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.UUID;

class NextMethodsDeserializer extends StdDeserializer<NextMethods> {

    protected NextMethodsDeserializer() {
        super(NextMethods.class);
    }

    @Override
    public NextMethods deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return NextMethods.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .activity(JsonNodeConverter.toObject(node.get("activity"), parser, Activity.class))
                .methods(JsonNodeConverter.toObject(node.get("methods"), parser, Methods.class))
                .protectSensitiveData(node.get("protectSensitiveData").asBoolean())
                .build();
    }

}
