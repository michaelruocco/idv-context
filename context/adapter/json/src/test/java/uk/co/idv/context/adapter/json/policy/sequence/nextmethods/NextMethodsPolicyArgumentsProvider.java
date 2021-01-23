package uk.co.idv.context.adapter.json.policy.sequence.nextmethods;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.entities.context.sequence.nextmethods.AnyOrderNextMethodsPolicy;
import uk.co.idv.context.entities.context.sequence.nextmethods.InOrderNextMethodsPolicy;

import java.util.stream.Stream;

public class NextMethodsPolicyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(InOrderNextMethodsPolicyJsonMother.build(), new InOrderNextMethodsPolicy()),
                Arguments.of(AnyOrderNextMethodsPolicyJsonMother.build(), new AnyOrderNextMethodsPolicy())
        );
    }

}
