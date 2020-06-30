package uk.co.idv.context.adapter.json.error;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static uk.co.idv.context.adapter.json.error.aliastype.UnsupportedAliasTypeErrorJsonMother.unsupportedAliasTypeErrorJson;
import static uk.co.idv.context.adapter.json.error.aliastype.UnsupportedAliasTypeErrorMother.unsupportedAliasTypeError;
import static uk.co.idv.context.adapter.json.error.internalserver.InternalServerErrorJsonMother.internalServerErrorJson;
import static uk.co.idv.context.adapter.json.error.internalserver.InternalServerErrorMother.internalServerError;
import static uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdErrorJsonMother.cannotUpdateIdvIdErrorJson;
import static uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdErrorMother.cannotUpdateIdvIdError;

public class ErrorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                internalServerErrorArguments(),
                cannotUpdateIdvIdErrorArguments(),
                unsupportedAliasTypeErrorArguments()
        );
    }

    private static Arguments internalServerErrorArguments() {
        return Arguments.of(
                internalServerErrorJson(),
                internalServerError()
        );
    }

    private static Arguments cannotUpdateIdvIdErrorArguments() {
        return Arguments.of(
                cannotUpdateIdvIdErrorJson(),
                cannotUpdateIdvIdError()
        );
    }

    private static Arguments unsupportedAliasTypeErrorArguments() {
        return Arguments.of(
                unsupportedAliasTypeErrorJson(),
                unsupportedAliasTypeError()
        );
    }

}
