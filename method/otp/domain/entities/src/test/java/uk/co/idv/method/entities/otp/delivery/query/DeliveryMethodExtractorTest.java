package uk.co.idv.method.entities.otp.delivery.query;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.OtpMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DeliveryMethodExtractorTest {

    private final UUID deliveryMethodId = UUID.fromString("0c207ec2-5e3e-488c-bdcb-e40576dac35d");

    private final DeliveryMethodExtractor extractor = new DeliveryMethodExtractor();

    @Test
    void shouldReturnEmptyIfMethodsIsEmpty() {
        Collection<Method> methods = Collections.emptyList();

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(methods, deliveryMethodId);

        assertThat(deliveryMethod).isEmpty();
    }

    @Test
    void shouldReturnEmptyIfMethodsDoesNotContainOtp() {
        Collection<Method> methods = Collections.singleton(FakeMethodMother.build());

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(methods, deliveryMethodId);

        assertThat(deliveryMethod).isEmpty();
    }

    @Test
    void shouldReturnEmptyIfMethodsDoesNotContainMethodWithDeliveryMethodWithId() {
        UUID otherId = UUID.fromString("7ba9be2f-38f9-4cb0-b991-f1c474763be9");
        Otp otp = OtpMother.withDeliveryMethod(DeliveryMethodMother.withId(otherId));
        Collection<Method> methods = Collections.singleton(otp);

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(methods, deliveryMethodId);

        assertThat(deliveryMethod).isEmpty();
    }

    @Test
    void shouldReturnOptionalDeliveryMethodIfMethodsContainsOtpWithDeliveryMethodIdThatMatches() {
        DeliveryMethod expectedDeliveryMethod = DeliveryMethodMother.withId(deliveryMethodId);
        Collection<Method> methods = Collections.singleton(OtpMother.withDeliveryMethod(expectedDeliveryMethod));

        Optional<DeliveryMethod> deliveryMethod = extractor.extractOptional(methods, deliveryMethodId);

        assertThat(deliveryMethod).contains(expectedDeliveryMethod);
    }

    @Test
    void shouldThrowExceptionIfNoMethodPresent() {
        Collection<Method> methods = Collections.emptyList();

        Throwable error = catchThrowable(() -> extractor.extract(methods, deliveryMethodId));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(deliveryMethodId.toString());
    }

    @Test
    void shouldThrowExceptionIfMethodsDoesNotContainOtp() {
        Collection<Method> methods = Collections.singleton(FakeMethodMother.build());

        Throwable error = catchThrowable(() -> extractor.extract(methods, deliveryMethodId));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(deliveryMethodId.toString());
    }

    @Test
    void shouldThrowExceptionMethodsDoesNotContainDeliveryMethodWithId() {
        UUID otherId = UUID.fromString("7ba9be2f-38f9-4cb0-b991-f1c474763be9");
        Otp otp = OtpMother.withDeliveryMethod(DeliveryMethodMother.withId(otherId));
        Collection<Method> methods = Collections.singleton(otp);

        Throwable error = catchThrowable(() -> extractor.extract(methods, deliveryMethodId));

        assertThat(error)
                .isInstanceOf(DeliveryMethodNotFoundException.class)
                .hasMessage(deliveryMethodId.toString());
    }

    @Test
    void shouldReturnDeliveryMethodIfMethodsContainsDeliveryMethodWithIdThatMatches() {
        DeliveryMethod expectedDeliveryMethod = DeliveryMethodMother.withId(deliveryMethodId);
        Collection<Method> methods = Collections.singleton(OtpMother.withDeliveryMethod(expectedDeliveryMethod));

        DeliveryMethod deliveryMethod = extractor.extract(methods, deliveryMethodId);

        assertThat(deliveryMethod).isEqualTo(expectedDeliveryMethod);
    }

}
