package uk.co.idv.context.adapter.json.channel.de;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.adapter.json.channel.RsaPropertyExtractor;
import uk.co.idv.context.entities.channel.de.DeRsa;

public class DeRsaChannelDeserializer extends StdDeserializer<DeRsa> {

    public DeRsaChannelDeserializer() {
        super(DeRsa.class);
    }

    @Override
    public DeRsa deserialize(JsonParser parser, DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return DeRsa.builder()
                .issuerSessionId(RsaPropertyExtractor.issuerSessionId(node))
                .dsSessionId(RsaPropertyExtractor.deSessionId(node))
                .build();
    }

}
