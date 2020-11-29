package uk.co.idv.common.adapter.json.error;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static uk.co.idv.common.adapter.json.error.badrequest.BadRequestErrorJsonMother.badRequestErrorWithMetaJson;
import static uk.co.idv.common.adapter.json.error.badrequest.BadRequestErrorMother.badRequestErrorWithMeta;
import static uk.co.idv.common.adapter.json.error.internalserver.InternalServerErrorJsonMother.internalServerErrorJson;
import static uk.co.idv.common.adapter.json.error.internalserver.InternalServerErrorJsonMother.internalServerErrorWithoutMessageJson;
import static uk.co.idv.common.adapter.json.error.internalserver.InternalServerErrorMother.internalServerError;
import static uk.co.idv.common.adapter.json.error.internalserver.InternalServerErrorMother.internalServerErrorWithoutMessage;

public class ErrorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                internalServerErrorArguments(),
                internalServerErrorWithoutMessageArguments(),
                badRequestErrorWithMetaArguments()
        );
    }

    private static Arguments internalServerErrorArguments() {
        return Arguments.of(
                internalServerErrorJson(),
                internalServerError()
        );
    }

    private static Arguments internalServerErrorWithoutMessageArguments() {
        return Arguments.of(
                internalServerErrorWithoutMessageJson(),
                internalServerErrorWithoutMessage()
        );
    }

    private static Arguments badRequestErrorWithMetaArguments() {
        return Arguments.of(
                badRequestErrorWithMetaJson(),
                badRequestErrorWithMeta()
        );
    }

}
