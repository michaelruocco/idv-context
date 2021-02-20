package uk.co.idv.context.adapter.json.context.create;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

class FacadeCreateContextRequestDeserializer extends StdDeserializer<FacadeCreateContextRequest> {

    protected FacadeCreateContextRequestDeserializer() {
        super(FacadeCreateContextRequest.class);
    }

    @Override
    public FacadeCreateContextRequest deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return FacadeCreateContextRequest.builder()
                .channel(JsonNodeConverter.toObject(node.get("channel"), parser, Channel.class))
                .activity(JsonNodeConverter.toObject(node.get("activity"), parser, Activity.class))
                .aliases(JsonNodeConverter.toObject(node.get("aliases"), parser, Aliases.class))
                .build();
    }

}
