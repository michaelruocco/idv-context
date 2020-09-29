package uk.co.idv.context.adapter.json.context;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.time.Instant;
import java.util.UUID;

class ContextDeserializer extends StdDeserializer<Context> {

    protected ContextDeserializer() {
        super(Context.class);
    }

    @Override
    public Context deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return Context.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .created(Instant.parse(node.get("created").asText()))
                .expiry(Instant.parse(node.get("expiry").asText()))
                .request(JsonNodeConverter.toObject(node.get("request"), parser, ServiceCreateContextRequest.class))
                .sequences(JsonNodeConverter.toObject(node.get("sequences"), parser, Sequences.class))
                .build();
    }

}
