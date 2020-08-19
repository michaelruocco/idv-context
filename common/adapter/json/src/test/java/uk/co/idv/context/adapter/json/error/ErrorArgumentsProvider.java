package uk.co.idv.context.adapter.json.error;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static uk.co.idv.context.adapter.json.error.internalserver.InternalServerErrorJsonMother.internalServerErrorJson;
import static uk.co.idv.context.adapter.json.error.internalserver.InternalServerErrorMother.internalServerError;

public class ErrorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(internalServerErrorArguments());
    }

    private static Arguments internalServerErrorArguments() {
        return Arguments.of(
                internalServerErrorJson(),
                internalServerError()
        );
    }

}
