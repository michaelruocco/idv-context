package uk.co.idv.context.adapter.json.lockout.policy.state.nonlocking;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.context.entities.lockout.policy.nonlocking.NonLockingStateCalculator;

public class NonLockingStateCalculatorDeserializer extends StdDeserializer<NonLockingStateCalculator> {

    public NonLockingStateCalculatorDeserializer() {
        super(NonLockingStateCalculator.class);
    }

    @Override
    public NonLockingStateCalculator deserialize(JsonParser parser, DeserializationContext context) {
        return new NonLockingStateCalculator();
    }

}
