package uk.co.idv.identity.adapter.json.eligibility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.mruoc.json.jackson.JsonNodeConverter;

public interface EligibilityFieldExtractor {

    static Channel toChannel(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("channel"), parser, Channel.class);
    }

    static Aliases toAliases(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("aliases"), parser, Aliases.class);
    }

    static RequestedData toRequestedData(JsonNode node, JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("requestedData"), parser, RequestedData.class);
    }

}
