package uk.co.idv.context.adapter.json.context.create;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.context.entities.policy.ContextPolicy;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class DefaultCreateContextRequestDeserializer extends StdDeserializer<ServiceCreateContextRequest> {

    protected DefaultCreateContextRequestDeserializer() {
        super(ServiceCreateContextRequest.class);
    }

    @Override
    public ServiceCreateContextRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return ServiceCreateContextRequest.builder()
                .initial(JsonNodeConverter.toObject(node.get("initial"), parser, FacadeCreateContextRequest.class))
                .policy(JsonNodeConverter.toObject(node.get("policy"), parser, ContextPolicy.class))
                .identity(JsonNodeConverter.toObject(node.get("identity"), parser, Identity.class))
                .build();
    }

}
