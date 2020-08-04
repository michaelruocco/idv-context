package uk.co.idv.context.adapter.json.lockout.policy.state;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.lockout.policy.state.hard.HardLockoutStateCalculatorJsonMother;
import uk.co.idv.context.adapter.json.lockout.policy.state.nonlocking.NonLockingStateCalculatorJsonMother;
import uk.co.idv.context.adapter.json.lockout.policy.state.soft.SoftLockoutStateCalculatorJsonMother;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutStateCalculatorMother;
import uk.co.idv.context.entities.lockout.policy.nonlocking.NonLockingStateCalculatorMother;
import uk.co.idv.context.entities.lockout.policy.soft.SoftLockoutStateCalculatorMother;

import java.util.stream.Stream;

public class LockoutStateCalculatorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(HardLockoutStateCalculatorJsonMother.build(), HardLockoutStateCalculatorMother.build()),
                Arguments.of(NonLockingStateCalculatorJsonMother.build(), NonLockingStateCalculatorMother.build()),
                Arguments.of(SoftLockoutStateCalculatorJsonMother.build(), SoftLockoutStateCalculatorMother.build())
        );
    }

}
