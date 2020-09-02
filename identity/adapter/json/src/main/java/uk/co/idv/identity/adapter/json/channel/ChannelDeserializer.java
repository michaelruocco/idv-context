package uk.co.idv.identity.adapter.json.channel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannel;
import uk.co.idv.identity.entities.channel.de.DeRsa;
import uk.co.idv.identity.entities.channel.gb.As3;
import uk.co.idv.identity.entities.channel.gb.GbRsa;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Map;

public class ChannelDeserializer extends StdDeserializer<Channel> {

    private final Map<String, Class<? extends Channel>> mappings;

    protected ChannelDeserializer() {
        this(buildMappings());
    }

    public ChannelDeserializer(Map<String, Class<? extends Channel>> mappings) {
        super(Channel.class);
        this.mappings = mappings;
    }

    @Override
    public Channel deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String type = extractId(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(type));
    }

    private static String extractId(JsonNode node) {
        return node.get("id").asText();
    }

    private Class<? extends Channel> toMappingType(String name) {
        return mappings.getOrDefault(name, DefaultChannel.class);
    }

    private static Map<String, Class<? extends Channel>> buildMappings() {
        return Map.of(
                GbRsa.ID, GbRsa.class,
                As3.ID, As3.class,
                DeRsa.ID, DeRsa.class
        );
    }

}
