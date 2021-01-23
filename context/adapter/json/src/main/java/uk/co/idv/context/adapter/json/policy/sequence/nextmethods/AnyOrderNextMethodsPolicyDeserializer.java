package uk.co.idv.context.adapter.json.policy.sequence.nextmethods;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.sequence.nextmethods.AnyOrderNextMethodsPolicy;


public class AnyOrderNextMethodsPolicyDeserializer extends StdDeserializer<AnyOrderNextMethodsPolicy> {

    public AnyOrderNextMethodsPolicyDeserializer() {
        super(AnyOrderNextMethodsPolicy.class);
    }

    @Override
    public AnyOrderNextMethodsPolicy deserialize(JsonParser parser, DeserializationContext context) {
        return new AnyOrderNextMethodsPolicy();
    }

}
