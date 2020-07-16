package uk.co.idv.context.adapter.json.channel.gb;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.adapter.json.channel.RsaFieldExtractor;
import uk.co.idv.context.entities.channel.gb.GbRsa;

public class GbRsaChannelDeserializer extends StdDeserializer<GbRsa> {

    public GbRsaChannelDeserializer() {
        super(GbRsa.class);
    }

    @Override
    public GbRsa deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return GbRsa.builder()
                .issuerSessionId(RsaFieldExtractor.issuerSessionId(node))
                .build();
    }

}
