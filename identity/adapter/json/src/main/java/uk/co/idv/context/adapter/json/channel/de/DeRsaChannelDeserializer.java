package uk.co.idv.context.adapter.json.channel.de;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.channel.RsaFieldExtractor;
import uk.co.idv.context.entities.channel.de.DeRsa;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class DeRsaChannelDeserializer extends StdDeserializer<DeRsa> {

    public DeRsaChannelDeserializer() {
        super(DeRsa.class);
    }

    @Override
    public DeRsa deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DeRsa.builder()
                .issuerSessionId(RsaFieldExtractor.issuerSessionId(node))
                .dsSessionId(RsaFieldExtractor.deSessionId(node))
                .build();
    }

}
