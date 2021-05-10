package uk.co.idv.identity.adapter.json.mobiledevice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.mobiledevice.MobileDevice;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Collection;

public class MobileDevicesDeserializer extends StdDeserializer<MobileDevices> {

    private static final TypeReference<Collection<MobileDevice>> MOBILE_DEVICE_COLLECTION = new TypeReference<>() {
        // intentionally blank
    };

    public MobileDevicesDeserializer() {
        super(MobileDevices.class);
    }

    @Override
    public MobileDevices deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new MobileDevices(JsonNodeConverter.toCollection(node, parser, MOBILE_DEVICE_COLLECTION));
    }

}
