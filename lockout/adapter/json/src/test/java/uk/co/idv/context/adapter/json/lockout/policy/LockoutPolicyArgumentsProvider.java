package uk.co.idv.context.adapter.json.lockout.policy;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.lockout.policy.state.hard.HardLockoutPolicyJsonMother;
import uk.co.idv.context.adapter.json.lockout.policy.state.nonlocking.NonLockingPolicyJsonMother;
import uk.co.idv.context.entities.lockout.policy.hard.HardLockoutPolicyMother;
import uk.co.idv.context.entities.lockout.policy.nonlocking.NonLockingPolicyMother;

import java.util.stream.Stream;

public class LockoutPolicyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(HardLockoutPolicyJsonMother.build(), HardLockoutPolicyMother.build()),
                Arguments.of(NonLockingPolicyJsonMother.build(), NonLockingPolicyMother.build())
        );
    }

}
