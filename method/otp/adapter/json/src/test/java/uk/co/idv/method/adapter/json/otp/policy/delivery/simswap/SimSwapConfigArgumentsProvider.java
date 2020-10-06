package uk.co.idv.method.adapter.json.otp.policy.delivery.simswap;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.method.adapter.json.otp.policy.delivery.phone.simswap.SimSwapConfigJsonMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.simswap.SimSwapConfigMother;

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
