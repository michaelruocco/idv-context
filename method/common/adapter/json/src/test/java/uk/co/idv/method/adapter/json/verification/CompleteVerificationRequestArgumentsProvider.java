package uk.co.idv.method.adapter.json.verification;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.method.entities.verification.CompleteVerificationRequestMother;

import java.util.stream.Stream;

public class CompleteVerificationRequestArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(CompleteVerificationRequestJsonMother.withTimestamp(), CompleteVerificationRequestMother.withTimestamp()),
                Arguments.of(CompleteVerificationRequestJsonMother.withoutTimestamp(), CompleteVerificationRequestMother.withoutTimestamp())
        );
    }

}
