package uk.co.idv.identity.adapter.json.mobiledevice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.mruoc.json.jackson.JsonNodeConverter;

import java.util.Optional;

public interface MobileDeviceExtractor {

    static MobileDevices toMobileDevices(JsonNode node, JsonParser parser) {
        return Optional.ofNullable(node.get("mobileDevices"))
                .map(mobileDevices -> JsonNodeConverter.toObject(mobileDevices, parser, MobileDevices.class))
                .orElseGet(MobileDevices::new);
    }

}
