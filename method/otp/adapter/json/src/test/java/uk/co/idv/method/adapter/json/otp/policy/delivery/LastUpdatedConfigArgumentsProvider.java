package uk.co.idv.method.adapter.json.otp.policy.delivery;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.method.adapter.json.otp.policy.delivery.phone.LastUpdatedConfigJsonMother;
import uk.co.idv.method.entities.otp.policy.delivery.LastUpdatedConfigMother;

import java.util.stream.Stream;

public class LastUpdatedConfigArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(LastUpdatedConfigJsonMother.build(), LastUpdatedConfigMother.unknownAllowed()),
                Arguments.of(LastUpdatedConfigJsonMother.withoutMinDays(), LastUpdatedConfigMother.withoutMinDaysSinceUpdate())
        );
    }

}
