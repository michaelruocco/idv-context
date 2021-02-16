package uk.co.idv.activity.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.activity.entities.Activity;
import uk.co.idv.activity.entities.DefaultActivity;
import uk.co.idv.activity.entities.Login;
import uk.co.idv.activity.entities.OnlinePurchase;
import uk.co.mruoc.json.jackson.JsonNodeConverter;
import uk.co.mruoc.json.jackson.JsonParserConverter;

import java.util.Map;

public class ActivityDeserializer extends StdDeserializer<Activity> {

    private final Map<String, Class<? extends Activity>> mappings;

    protected ActivityDeserializer() {
        this(buildMappings());
    }

    public ActivityDeserializer(Map<String, Class<? extends Activity>> mappings) {
        super(Activity.class);
        this.mappings = mappings;
    }

    @Override
    public Activity deserialize(JsonParser parser, DeserializationContext context) {
        JsonNode node = JsonParserConverter.toNode(parser);
        String name = extractName(node);
        return JsonNodeConverter.toObject(node, parser, toMappingType(name));
    }

    private static String extractName(JsonNode node) {
        return node.get("name").asText();
    }

    private Class<? extends Activity> toMappingType(String name) {
        return mappings.getOrDefault(name, DefaultActivity.class);
    }

    private static Map<String, Class<? extends Activity>> buildMappings() {
        return Map.of(
                Login.NAME, Login.class,
                OnlinePurchase.NAME, OnlinePurchase.class
        );
    }

}
