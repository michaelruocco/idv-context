package uk.co.idv.context.adapter.json.context.create;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class DefaultCreateContextRequestDeserializer extends StdDeserializer<DefaultCreateContextRequest> {

    protected DefaultCreateContextRequestDeserializer() {
        super(DefaultCreateContextRequest.class);
    }

    @Override
    public DefaultCreateContextRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultCreateContextRequest.builder()
                .initial(JsonNodeConverter.toObject(node.get("initial"), parser, FacadeCreateContextRequest.class))
                .policy(JsonNodeConverter.toObject(node.get("policy"), parser, ContextPolicy.class))
                .identity(JsonNodeConverter.toObject(node.get("identity"), parser, Identity.class))
                .build();
    }

}
