package uk.co.idv.context.adapter.json.context.sequence;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class SequencesDeserializer extends StdDeserializer<Sequences> {

    private static final TypeReference<Collection<Sequence>> SEQUENCE_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    protected SequencesDeserializer() {
        super(Sequences.class);
    }

    @Override
    public Sequences deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new Sequences(JsonNodeConverter.toCollection(node, parser, SEQUENCE_COLLECTION));
    }

}
