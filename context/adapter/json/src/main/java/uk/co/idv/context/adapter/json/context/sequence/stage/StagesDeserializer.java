package uk.co.idv.context.adapter.json.context.sequence.stage;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.sequence.stage.Stage;
import uk.co.idv.context.entities.context.sequence.stage.Stages;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class StagesDeserializer extends StdDeserializer<Stages> {

    private static final TypeReference<Collection<Stage>> SEQUENCE_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    protected StagesDeserializer() {
        super(Stages.class);
    }

    @Override
    public Stages deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new Stages(JsonNodeConverter.toCollection(node, parser, SEQUENCE_COLLECTION));
    }

}
