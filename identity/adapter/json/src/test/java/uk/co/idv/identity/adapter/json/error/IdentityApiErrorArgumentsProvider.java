package uk.co.idv.identity.adapter.json.error;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static uk.co.idv.identity.adapter.json.error.aliastype.UnsupportedAliasTypeErrorJsonMother.unsupportedAliasTypeErrorJson;
import static uk.co.idv.identity.adapter.json.error.aliastype.UnsupportedAliasTypeErrorMother.unsupportedAliasTypeError;
import static uk.co.idv.identity.adapter.json.error.country.mismatch.CountryMismatchErrorJsonMother.countryMismatchErrorJson;
import static uk.co.idv.identity.adapter.json.error.country.mismatch.CountryMismatchErrorMother.countryMismatchError;
import static uk.co.idv.identity.adapter.json.error.country.notprovided.CountryNotProvidedErrorJsonMother.countryNotProvidedErrorJson;
import static uk.co.idv.identity.adapter.json.error.country.notprovided.CountryNotProvidedErrorMother.countryNotProvidedError;
import static uk.co.idv.identity.adapter.json.error.eligibilitynotconfigured.EligibilityNotConfiguredErrorJsonMother.eligibilityNotConfiguredErrorJson;
import static uk.co.idv.identity.adapter.json.error.eligibilitynotconfigured.EligibilityNotConfiguredErrorMother.eligibilityNotConfiguredError;
import static uk.co.idv.identity.adapter.json.error.identitynotfound.IdentityNotFoundErrorJsonMother.identityNotFoundErrorJson;
import static uk.co.idv.identity.adapter.json.error.identitynotfound.IdentityNotFoundErrorMother.identityNotFoundError;
import static uk.co.idv.identity.adapter.json.error.updateidvid.CannotUpdateIdvIdErrorJsonMother.cannotUpdateIdvIdErrorJson;
import static uk.co.idv.identity.adapter.json.error.updateidvid.CannotUpdateIdvIdErrorMother.cannotUpdateIdvIdError;

public class IdentityApiErrorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                cannotUpdateIdvIdErrorArguments(),
                unsupportedAliasTypeErrorArguments(),
                identityNotFoundErrorArguments(),
                countryMismatchErrorArguments(),
                countryNotProvidedErrorArguments(),
                eligibilityNotConfiguredErrorArguments()
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

    private static Arguments identityNotFoundErrorArguments() {
        return Arguments.of(
                identityNotFoundErrorJson(),
                identityNotFoundError()
        );
    }

    private static Arguments countryMismatchErrorArguments() {
        return Arguments.of(
                countryMismatchErrorJson(),
                countryMismatchError()
        );
    }

    private static Arguments countryNotProvidedErrorArguments() {
        return Arguments.of(
                countryNotProvidedErrorJson(),
                countryNotProvidedError()
        );
    }

    private static Arguments eligibilityNotConfiguredErrorArguments() {
        return Arguments.of(
                eligibilityNotConfiguredErrorJson(),
                eligibilityNotConfiguredError()
        );
    }

}
