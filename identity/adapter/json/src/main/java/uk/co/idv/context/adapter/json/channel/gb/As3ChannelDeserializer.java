package uk.co.idv.context.adapter.json.channel.gb;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.channel.gb.As3;

public class As3ChannelDeserializer extends StdDeserializer<As3> {

    public As3ChannelDeserializer() {
        super(As3.class);
    }

    @Override
    public As3 deserialize(JsonParser parser, DeserializationContext context) {
        return new As3();
    }

}
