package uk.co.idv.context.adapter.json.error;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.context.error.contextexpired.ContextExpiredErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.contextexpired.ContextExpiredErrorMother;
import uk.co.idv.context.adapter.json.context.error.contextnotfound.ContextNotFoundErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.contextnotfound.ContextNotFoundErrorMother;

import java.util.stream.Stream;

public class ContextApiErrorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                contextExpiredErrorArguments(),
                contextNotFoundErrorArguments()
        );
    }

    private static Arguments contextExpiredErrorArguments() {
        return Arguments.of(
                ContextExpiredErrorJsonMother.contextExpiredErrorJson(),
                ContextExpiredErrorMother.contextExpiredError()
        );
    }

    private static Arguments contextNotFoundErrorArguments() {
        return Arguments.of(
                ContextNotFoundErrorJsonMother.contextNotFoundErrorJson(),
                ContextNotFoundErrorMother.contextNotFoundError()
        );
    }

}
