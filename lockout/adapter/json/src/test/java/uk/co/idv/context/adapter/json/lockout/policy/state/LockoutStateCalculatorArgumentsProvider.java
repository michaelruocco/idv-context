package uk.co.idv.context.adapter.json.lockout.policy.state;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.lockout.policy.state.hard.HardLockoutStateCalculatorJsonMother;
import uk.co.idv.context.adapter.json.lockout.policy.state.nonlocking.NonLockingStateCalculatorJsonMother;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.context.entities.lockout.policy.nonlocking.NonLockingStateCalculator;

import java.util.stream.Stream;

public class LockoutStateCalculatorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(HardLockoutStateCalculatorJsonMother.build(), new HardLockoutStateCalculator(3)),
                Arguments.of(NonLockingStateCalculatorJsonMother.build(), new NonLockingStateCalculator())
        );
    }

}
