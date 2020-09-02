package uk.co.idv.lockout.adapter.json.policy.state;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.lockout.adapter.json.policy.state.hard.HardLockoutStateCalculatorJsonMother;
import uk.co.idv.lockout.adapter.json.policy.state.nonlocking.NonLockingStateCalculatorJsonMother;
import uk.co.idv.lockout.adapter.json.policy.state.soft.RecurringSoftLockoutStateCalculatorJsonMother;
import uk.co.idv.lockout.adapter.json.policy.state.soft.SoftLockoutStateCalculatorJsonMother;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateCalculatorMother;
import uk.co.idv.lockout.entities.policy.nonlocking.NonLockingStateCalculatorMother;
import uk.co.idv.lockout.entities.policy.soft.RecurringSoftLockoutStateCalculatorMother;
import uk.co.idv.lockout.entities.policy.soft.SoftLockoutStateCalculatorMother;

import java.util.stream.Stream;

public class LockoutStateCalculatorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(HardLockoutStateCalculatorJsonMother.build(), HardLockoutStateCalculatorMother.build()),
                Arguments.of(NonLockingStateCalculatorJsonMother.build(), NonLockingStateCalculatorMother.build()),
                Arguments.of(SoftLockoutStateCalculatorJsonMother.build(), SoftLockoutStateCalculatorMother.build()),
                Arguments.of(RecurringSoftLockoutStateCalculatorJsonMother.build(), RecurringSoftLockoutStateCalculatorMother.build())
        );
    }

}
