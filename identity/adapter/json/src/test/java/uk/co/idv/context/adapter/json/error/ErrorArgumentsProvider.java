package uk.co.idv.context.adapter.json.error;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.error.internalserver.InternalServerErrorMother;
import uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdErrorJsonMother;
import uk.co.idv.context.adapter.json.error.internalserver.InternalServerErrorJsonMother;
import uk.co.idv.context.adapter.json.error.updateidvid.CannotUpdateIdvIdErrorMother;

import java.util.stream.Stream;

public class ErrorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                internalServerErrorArguments(),
                cannotUpdateIdvIdErrorArguments()
        );
    }

    private static Arguments internalServerErrorArguments() {
        return Arguments.of(
                InternalServerErrorJsonMother.internalServerError(),
                InternalServerErrorMother.internalServerError()
        );
    }

    private static Arguments cannotUpdateIdvIdErrorArguments() {
        return Arguments.of(
                CannotUpdateIdvIdErrorJsonMother.cannotUpdateIdvIdError(),
                CannotUpdateIdvIdErrorMother.cannotUpdateIdvIdError()
        );
    }

}
