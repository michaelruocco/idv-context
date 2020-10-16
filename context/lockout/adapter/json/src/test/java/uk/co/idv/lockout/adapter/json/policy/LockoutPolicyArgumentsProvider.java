package uk.co.idv.lockout.adapter.json.policy;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.lockout.adapter.json.policy.state.hard.HardLockoutPolicyJsonMother;
import uk.co.idv.lockout.adapter.json.policy.state.nonlocking.NonLockingPolicyJsonMother;
import uk.co.idv.lockout.adapter.json.policy.state.soft.RecurringSoftLockoutPolicyJsonMother;
import uk.co.idv.lockout.adapter.json.policy.state.soft.SoftLockoutPolicyJsonMother;
import uk.co.idv.lockout.entities.policy.hard.HardLockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.nonlocking.NonLockingPolicyMother;
import uk.co.idv.lockout.entities.policy.soft.RecurringSoftLockoutPolicyMother;
import uk.co.idv.lockout.entities.policy.soft.SoftLockoutPolicyMother;

import java.util.stream.Stream;

public class LockoutPolicyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(HardLockoutPolicyJsonMother.build(), HardLockoutPolicyMother.build()),
                Arguments.of(NonLockingPolicyJsonMother.build(), NonLockingPolicyMother.build()),
                Arguments.of(SoftLockoutPolicyJsonMother.build(), SoftLockoutPolicyMother.build()),
                Arguments.of(RecurringSoftLockoutPolicyJsonMother.build(), RecurringSoftLockoutPolicyMother.build())
        );
    }

}
