package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.entities.context.method.otp.delivery.EmailDeliveryMethodMother;
import uk.co.idv.context.entities.context.method.otp.delivery.SmsDeliveryMethodMother;
import uk.co.idv.context.entities.context.method.otp.delivery.VoiceDeliveryMethodMother;

import java.util.stream.Stream;

public class DeliveryMethodArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(SmsDeliveryMethodJsonMother.sms(), SmsDeliveryMethodMother.sms()),
                Arguments.of(VoiceDeliveryMethodJsonMother.voice(), VoiceDeliveryMethodMother.voice()),
                Arguments.of(EmailDeliveryMethodJsonMother.email(), EmailDeliveryMethodMother.email())
        );
    }

}
