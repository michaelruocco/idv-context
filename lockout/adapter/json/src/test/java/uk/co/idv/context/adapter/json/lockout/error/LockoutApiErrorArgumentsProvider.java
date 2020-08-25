package uk.co.idv.context.adapter.json.lockout.error;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static uk.co.idv.context.adapter.json.lockout.error.lockedout.LockedOutErrorJsonMother.lockedOutErrorJson;
import static uk.co.idv.context.adapter.json.lockout.error.lockedout.LockedOutErrorMother.lockedOutError;

public class LockoutApiErrorArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(lockedOutErrorArguments());
    }

    private static Arguments lockedOutErrorArguments() {
        return Arguments.of(
                lockedOutErrorJson(),
                lockedOutError()
        );
    }

}
