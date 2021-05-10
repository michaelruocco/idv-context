package uk.co.idv.identity.adapter.json.mobiledevice;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.identity.entities.mobiledevice.MobileDevice;
import uk.co.mruoc.json.jackson.JsonParserConverter;

public class MobileDeviceDeserializer extends StdDeserializer<MobileDevice> {

    protected MobileDeviceDeserializer() {
        super(MobileDevice.class);
    }

    @Override
    public MobileDevice deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        return new MobileDevice(node.get("token").asText());
    }

}
