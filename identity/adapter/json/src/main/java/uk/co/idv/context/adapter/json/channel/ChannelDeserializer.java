package uk.co.idv.context.adapter.json.channel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.adapter.json.JsonNodeConverter;
import uk.co.idv.context.adapter.json.JsonParserConverter;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannel;
import uk.co.idv.context.entities.channel.de.DeRsa;
import uk.co.idv.context.entities.channel.gb.As3;
import uk.co.idv.context.entities.channel.gb.GbRsa;

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
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String type = extractId(node);
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
