package uk.co.idv.context.adapter.json.policy.method.otp.delivery.simswap;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.simswap.SimSwapConfigJsonMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap.SimSwapConfigMother;

import java.util.stream.Stream;

public class SimSwapConfigArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(SimSwapConfigJsonMother.build(), SimSwapConfigMother.build()),
                Arguments.of(SimSwapConfigJsonMother.withoutMinDays(), SimSwapConfigMother.withoutMinDays())
        );
    }

}
