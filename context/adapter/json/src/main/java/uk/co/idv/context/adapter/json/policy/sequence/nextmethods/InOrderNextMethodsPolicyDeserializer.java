package uk.co.idv.context.adapter.json.policy.sequence.nextmethods;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.context.sequence.nextmethods.InOrderNextMethodsPolicy;


public class InOrderNextMethodsPolicyDeserializer extends StdDeserializer<InOrderNextMethodsPolicy> {

    public InOrderNextMethodsPolicyDeserializer() {
        super(InOrderNextMethodsPolicy.class);
    }

    @Override
    public InOrderNextMethodsPolicy deserialize(JsonParser parser, DeserializationContext context) {
        return new InOrderNextMethodsPolicy();
    }

}
