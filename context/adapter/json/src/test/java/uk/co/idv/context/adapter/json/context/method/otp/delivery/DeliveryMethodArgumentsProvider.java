package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.method.entities.otp.delivery.email.EmailDeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.phone.sms.SmsDeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.phone.voice.VoiceDeliveryMethodMother;

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
