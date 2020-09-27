package uk.co.idv.identity.adapter.json.channel.gb;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.channel.gb.Abc;

public class AbcChannelDeserializer extends StdDeserializer<Abc> {

    public AbcChannelDeserializer() {
        super(Abc.class);
    }

    @Override
    public Abc deserialize(JsonParser parser, DeserializationContext context) {
        return new Abc();
    }

}
