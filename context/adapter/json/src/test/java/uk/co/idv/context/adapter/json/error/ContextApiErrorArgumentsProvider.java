package uk.co.idv.context.adapter.json.error;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.context.error.policynotconfigured.ContextPolicyNotConfiguredErrorJsonMother;
import uk.co.idv.context.adapter.json.context.error.policynotconfigured.ContextPolicyNotConfiguredErrorMother;
import uk.co.idv.method.adapter.json.error.contextexpired.ContextExpiredErrorJsonMother;
import uk.co.idv.method.adapter.json.error.contextexpired.ContextExpiredErrorMother;
import uk.co.idv.method.adapter.json.error.contextnotfound.ContextNotFoundErrorJsonMother;
import uk.co.idv.method.adapter.json.error.contextnotfound.ContextNotFoundErrorMother;
import uk.co.idv.method.adapter.json.error.notnextmethod.NotNextMethodErrorJsonMother;
import uk.co.idv.method.adapter.json.error.notnextmethod.NotNextMethodErrorMother;

import java.util.stream.Stream;

public class ContextApiErrorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                contextExpiredErrorArguments(),
                contextNotFoundErrorArguments(),
                contextPolicyNotConfiguredErrorArguments(),
                notNextMethodErrorArguments()
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

    private static Arguments contextPolicyNotConfiguredErrorArguments() {
        return Arguments.of(
                ContextPolicyNotConfiguredErrorJsonMother.contextPolicyNotConfiguredErrorJson(),
                ContextPolicyNotConfiguredErrorMother.contextPolicyNotConfiguredError()
        );
    }

    private static Arguments notNextMethodErrorArguments() {
        return Arguments.of(
                NotNextMethodErrorJsonMother.notNextMethodErrorJson(),
                NotNextMethodErrorMother.notNextMethodError()
        );
    }

}
