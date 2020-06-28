package uk.co.idv.context.adapter.json.channel.gb;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.channel.gb.GbAs3;

public class As3ChannelDeserializer extends StdDeserializer<GbAs3> {

    public As3ChannelDeserializer() {
        super(GbAs3.class);
    }

    @Override
    public GbAs3 deserialize(JsonParser parser, DeserializationContext context) {
        return new GbAs3();
    }

}
