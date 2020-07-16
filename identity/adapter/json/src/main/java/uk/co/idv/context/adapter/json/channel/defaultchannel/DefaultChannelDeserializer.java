package uk.co.idv.context.adapter.json.channel.defaultchannel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.channel.DefaultChannel;

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
