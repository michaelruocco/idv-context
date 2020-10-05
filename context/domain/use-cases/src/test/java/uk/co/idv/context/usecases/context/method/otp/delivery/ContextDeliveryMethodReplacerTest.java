package uk.co.idv.context.usecases.context.method.otp.delivery;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.method.entities.eligibility.EligibilityMother;
import uk.co.idv.method.entities.method.FakeMethodMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.otp.OtpMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodsMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ContextDeliveryMethodReplacerTest {

    private final ContextDeliveryMethodReplacer replacer = new ContextDeliveryMethodReplacer();

    @Test
    void shouldNotChangeMethodThatIsNotOtp() {
        Method method = FakeMethodMother.build();
        Context context = ContextMother.withMethod(method);
        DeliveryMethods deliveryMethods = DeliveryMethodsMother.oneOfEach();

        Context updated = replacer.replace(context, deliveryMethods);

        assertThat(updated).isEqualTo(context);
    }

    @Test
    void shouldNotAddDeliveryMethodIfMethodWithIdDoesNotExistOnContext() {
        UUID id = UUID.fromString("a34077cd-ee91-471d-a8b9-6a881a073c77");
        Method method = OtpMother.withDeliveryMethod(DeliveryMethodMother.withId(id));
        Context context = ContextMother.withMethod(method);
        DeliveryMethod updatedMethod = DeliveryMethodMother.withId(UUID.fromString("e8152922-166e-4839-9a62-4a1f9fb4aa3f"));

        Context updated = replacer.replace(context, DeliveryMethodsMother.with(updatedMethod));

        assertThat(updated).isEqualTo(context);
    }

    @Test
    void shouldReplaceDeliveryMethodWithSameIdOnContext() {
        UUID id = UUID.fromString("a34077cd-ee91-471d-a8b9-6a881a073c77");
        Method method = OtpMother.withDeliveryMethod(DeliveryMethodMother.withId(id));
        Context context = ContextMother.withMethod(method);
        DeliveryMethod updatedMethod = DeliveryMethodMother.builder()
                .id(id)
                .eligibility(EligibilityMother.ineligible())
                .build();

        Context updated = replacer.replace(context, DeliveryMethodsMother.with(updatedMethod));

        Context expectedContext = ContextMother.withMethod(OtpMother.withDeliveryMethod(updatedMethod));
        assertThat(updated).isEqualTo(expectedContext);
    }

}
