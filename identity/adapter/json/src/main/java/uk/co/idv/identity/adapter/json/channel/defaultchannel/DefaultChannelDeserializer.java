package uk.co.idv.identity.adapter.json.channel.defaultchannel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.identity.entities.channel.DefaultChannel;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class DefaultChannelDeserializer extends StdDeserializer<DefaultChannel> {

    public DefaultChannelDeserializer() {
        super(DefaultChannel.class);
    }

    @Override
    public DefaultChannel deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultChannel.builder()
                .id(node.get("id").asText())
                .country(extractCountry(node))
                .build();
    }

    private CountryCode extractCountry(JsonNode node) {
        return CountryCode.valueOf(node.get("country").asText());
    }

}
