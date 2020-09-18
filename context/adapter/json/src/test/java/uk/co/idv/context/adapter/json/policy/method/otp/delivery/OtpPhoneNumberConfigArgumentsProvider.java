package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone.OtpPhoneNumberConfigJsonMother;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.OtpPhoneNumberConfigMother;

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
