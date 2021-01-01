package uk.co.idv.method.entities.otp.delivery.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.sequence.SequencesMother;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.OtpMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DeliveryMethodExtractorTest {

    private final UUID deliveryMethodId = UUID.fromString("0c207ec2-5e3e-488c-bdcb-e40576dac35d");

    private final DeliveryMethodExtractor extractor = new DeliveryMethodExtractor();

    @Test
    void shouldReturnEmptyIfNoNextMethodPresent() {
        Context context = ContextMother.withSequences(SequencesMother.empty());

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(context, deliveryMethodId);

        assertThat(deliveryMethod).isEmpty();
    }

    @Test
    void shouldReturnEmptyIfNextMethodNotOtp() {
        Context context = ContextMother.withMethod(FakeMethodMother.build());

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(context, deliveryMethodId);

        assertThat(deliveryMethod).isEmpty();
    }

    @Test
    void shouldReturnEmptyIfMethodDoesNotHaveDeliveryMethodWithId() {
        UUID otherId = UUID.fromString("7ba9be2f-38f9-4cb0-b991-f1c474763be9");
        Otp otp = OtpMother.withDeliveryMethod(DeliveryMethodMother.withId(otherId));
        Context context = ContextMother.withMethod(otp);

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(context, deliveryMethodId);

        assertThat(deliveryMethod).isEmpty();
    }

    @Test
    void shouldReturnOptionalDeliveryMethodIfIdMatches() {
        DeliveryMethod expectedDeliveryMethod = DeliveryMethodMother.withId(deliveryMethodId);
        Context context = ContextMother.withMethod(OtpMother.withDeliveryMethod(expectedDeliveryMethod));

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(context, deliveryMethodId);

        assertThat(deliveryMethod).contains(expectedDeliveryMethod);
    }

    @Test
    void shouldThrowExceptionIfNoNextMethodPresent() {
        Context context = ContextMother.withSequences(SequencesMother.empty());

        Throwable error = catchThrowable(() -> extractor.extract(context, deliveryMethodId));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(deliveryMethodId.toString());
    }

    @Test
    void shouldThrowExceptionIfNextMethodIsNotOtp() {
        Context context = ContextMother.withMethod(FakeMethodMother.build());

        Throwable error = catchThrowable(() -> extractor.extract(context, deliveryMethodId));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(deliveryMethodId.toString());
    }

    @Test
    void shouldThrowExceptionIfDoesNotHaveDeliveryMethodWithId() {
        UUID otherId = UUID.fromString("7ba9be2f-38f9-4cb0-b991-f1c474763be9");
        Otp otp = OtpMother.withDeliveryMethod(DeliveryMethodMother.withId(otherId));
        Context context = ContextMother.withMethod(otp);

        Throwable error = catchThrowable(() -> extractor.extract(context, deliveryMethodId));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(deliveryMethodId.toString());
    }

    @Test
    void shouldReturnDeliveryMethodIfIdMatches() {
        DeliveryMethod expectedDeliveryMethod = DeliveryMethodMother.withId(deliveryMethodId);
        Context context = ContextMother.withMethod(OtpMother.withDeliveryMethod(expectedDeliveryMethod));

        DeliveryMethod deliveryMethod = extractor.extract(context, deliveryMethodId);

        assertThat(deliveryMethod).isEqualTo(expectedDeliveryMethod);
    }

}
