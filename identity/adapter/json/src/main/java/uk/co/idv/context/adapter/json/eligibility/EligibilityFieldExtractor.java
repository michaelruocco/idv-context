package uk.co.idv.context.adapter.json.eligibility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.context.adapter.json.JsonNodeConverter;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.channel.Channel;

import java.util.Collection;

public interface EligibilityFieldExtractor {

    static Channel toChannel(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("channel"), parser, Channel.class);
    }

    static Aliases toAliases(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("aliases"), parser, Aliases.class);
    }

    static Collection<String> toRequested(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toStringCollection(node.get("requested"), parser);
    }

}
