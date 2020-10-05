package uk.co.idv.context.adapter.json.policy.method;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.policy.method.fake.FakeMethodPolicyJsonMother;
import uk.co.idv.method.entities.fake.policy.FakeMethodPolicyMother;

import java.util.stream.Stream;

public class MethodPolicyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(Arguments.of(FakeMethodPolicyJsonMother.build(), FakeMethodPolicyMother.build()));
    }

}
