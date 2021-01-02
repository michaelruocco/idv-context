package uk.co.idv.method.adapter.otp.protect.mask;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddressMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.phone.OtpPhoneNumberMother;

import java.util.Map;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class DeliveryMethodMaskerTest {

    private final Map<String, UnaryOperator<String>> typeMaskers = buildTypeMaskers();

    private final DeliveryMethodMasker masker = new DeliveryMethodMasker(typeMaskers);

    @Test
    void shouldApplyTypeMaskerIfPresent() {
        DeliveryMethod method = DeliveryMethodMother.emailWithValue(EmailAddressMother.bugsBunny());

        DeliveryMethod maskedMethod = masker.apply(method);

        assertThat(maskedMethod).usingRecursiveComparison().ignoringFields("value").isEqualTo(method);
        assertThat(maskedMethod.getValue()).isEqualTo("********************");
    }

    @Test
    void shouldThrowExceptionIfNoApplicableTypeMaskerPresent() {
        DeliveryMethod method = DeliveryMethodMother.smsWithValue(OtpPhoneNumberMother.localMobile());

        Throwable error = catchThrowable(() -> masker.apply(method));

        assertThat(error)
                .isInstanceOf(DeliveryMethodMaskingNotSupportedException.class)
                .hasMessage(method.getType());
    }

    private static Map<String, UnaryOperator<String>> buildTypeMaskers() {
        return Map.of("email", s -> StringUtils.repeat('*', s.length()));
    }

}
