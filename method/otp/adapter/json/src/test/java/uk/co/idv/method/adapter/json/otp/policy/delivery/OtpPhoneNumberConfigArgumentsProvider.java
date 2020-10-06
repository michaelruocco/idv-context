package uk.co.idv.method.adapter.json.otp.policy.delivery;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.method.adapter.json.otp.policy.delivery.phone.OtpPhoneNumberConfigJsonMother;
import uk.co.idv.method.entities.otp.policy.delivery.phone.OtpPhoneNumberConfigMother;

import java.util.stream.Stream;

public class OtpPhoneNumberConfigArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(OtpPhoneNumberConfigJsonMother.build(), OtpPhoneNumberConfigMother.build()),
                Arguments.of(OtpPhoneNumberConfigJsonMother.withoutSimSwapConfig(), OtpPhoneNumberConfigMother.withoutSimSwapConfig())
        );
    }

}
