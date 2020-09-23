package uk.co.idv.context.adapter.json.context.method;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.context.method.otp.OtpJsonMother;
import uk.co.idv.context.entities.context.method.otp.OtpMother;

import java.util.stream.Stream;

public class MethodArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(OtpJsonMother.build(), OtpMother.build())
        );
    }

}
