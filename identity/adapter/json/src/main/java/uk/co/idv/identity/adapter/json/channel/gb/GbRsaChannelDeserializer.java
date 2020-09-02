package uk.co.idv.identity.adapter.json.channel.gb;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.adapter.json.channel.RsaFieldExtractor;
import uk.co.idv.identity.entities.channel.gb.GbRsa;
import uk.co.mruoc.json.jackson.JsonParserConverter;

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
