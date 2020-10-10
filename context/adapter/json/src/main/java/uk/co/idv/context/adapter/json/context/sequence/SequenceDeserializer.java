package uk.co.idv.context.adapter.json.context.sequence;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;


class SequenceDeserializer extends StdDeserializer<Sequence> {

    protected SequenceDeserializer() {
        super(Sequence.class);
    }

    @Override
    public Sequence deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Sequence.builder()
                .name(node.get("name").asText())
                .methods(JsonNodeConverter.toObject(node.get("methods"), parser, Methods.class))
                .build();
    }

}
