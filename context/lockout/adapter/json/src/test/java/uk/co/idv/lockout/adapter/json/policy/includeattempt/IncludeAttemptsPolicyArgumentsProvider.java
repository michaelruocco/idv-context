package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAllAttemptsPolicy;
import uk.co.idv.lockout.entities.policy.includeattempt.IncludeAttemptsWithinDurationPolicyMother;

import java.util.stream.Stream;

public class IncludeAttemptsPolicyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(IncludeAllAttemptsPolicyJsonMother.json(), new IncludeAllAttemptsPolicy()),
                Arguments.of(IncludeAttemptsWithinDurationPolicyJsonMother.json(), IncludeAttemptsWithinDurationPolicyMother.build())
        );
    }

}
