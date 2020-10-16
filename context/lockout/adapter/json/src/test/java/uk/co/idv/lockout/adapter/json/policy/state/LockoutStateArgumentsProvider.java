package uk.co.idv.lockout.adapter.json.policy.state;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.lockout.adapter.json.policy.state.hard.HardLockoutStateJsonMother;
import uk.co.idv.lockout.adapter.json.policy.state.nonlocking.NonLockingStateJsonMother;
import uk.co.idv.lockout.adapter.json.policy.state.soft.SoftLockoutStateJsonMother;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutStateMother;
import uk.co.idv.lockout.entities.policy.nonlocking.NonLockingStateMother;
import uk.co.idv.lockout.entities.policy.soft.SoftLockoutStateMother;

import java.util.stream.Stream;

public class LockoutStateArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(HardLockoutStateJsonMother.build(), HardLockoutStateMother.build()),
                Arguments.of(NonLockingStateJsonMother.build(), NonLockingStateMother.build()),
                Arguments.of(SoftLockoutStateJsonMother.build(), SoftLockoutStateMother.build())
        );
    }

}
