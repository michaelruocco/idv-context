package uk.co.idv.context.adapter.json.policy.method;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.policy.method.otp.OtpPolicyJsonMother;
import uk.co.idv.context.entities.policy.method.otp.OtpPolicyMother;

import java.util.stream.Stream;

public class MethodPolicyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(Arguments.of(OtpPolicyJsonMother.build(), OtpPolicyMother.build()));
    }

}
