package uk.co.idv.method.usecases.otp.delivery;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.eligibility.EligibilityMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;
import uk.co.idv.method.entities.otp.Otp;
import uk.co.idv.method.entities.otp.OtpMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodsMother;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OtpDeliveryMethodReplacerTest {

    @Test
    void shouldNotChangeMethodThatIsNotOtp() {
        OtpDeliveryMethodReplacer replacer = new OtpDeliveryMethodReplacer(DeliveryMethodsMother.oneOfEach());
        Method method = FakeMethodMother.build();

        Method updated = replacer.apply(method);

        assertThat(updated).isEqualTo(method);
    }

    @Test
    void shouldNotAddDeliveryMethodIfMethodWithIdDoesNotExist() {
        UUID id = UUID.fromString("a34077cd-ee91-471d-a8b9-6a881a073c77");
        Method method = OtpMother.withDeliveryMethod(DeliveryMethodMother.withId(id));
        DeliveryMethod updatedMethod = DeliveryMethodMother.withId(UUID.fromString("e8152922-166e-4839-9a62-4a1f9fb4aa3f"));
        OtpDeliveryMethodReplacer replacer = new OtpDeliveryMethodReplacer(DeliveryMethodsMother.with(updatedMethod));

        Method updated = replacer.apply(method);

        assertThat(updated).isEqualTo(method);
    }

    @Test
    void shouldReplaceDeliveryMethodWithSameId() {
        UUID id = UUID.fromString("a34077cd-ee91-471d-a8b9-6a881a073c77");
        Method method = OtpMother.withDeliveryMethod(DeliveryMethodMother.withId(id));
        DeliveryMethod updatedMethod = DeliveryMethodMother.builder()
                .id(id)
                .eligibility(EligibilityMother.ineligible())
                .build();
        OtpDeliveryMethodReplacer replacer = new OtpDeliveryMethodReplacer(DeliveryMethodsMother.with(updatedMethod));

        Method updated = replacer.apply(method);

        Otp expectedMethod = OtpMother.withDeliveryMethod(updatedMethod);
        assertThat(updated).isEqualTo(expectedMethod);
    }

}
