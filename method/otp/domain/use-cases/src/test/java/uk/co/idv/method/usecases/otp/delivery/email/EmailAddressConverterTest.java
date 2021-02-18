package uk.co.idv.method.usecases.otp.delivery.email;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddressMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EmailAddressConverterTest {

    private static final EmailAddress EMAIL_ADDRESS = EmailAddressMother.joeBloggsHotmail();

    private final DeliveryMethodConfig config = mock(DeliveryMethodConfig.class);
    private final UuidGenerator uuidGenerator = mock(UuidGenerator.class);

    private final EmailAddressConverter converter = new EmailAddressConverter(uuidGenerator);

    @Test
    void shouldPopulateIdOnDeliveryMethod() {
        UUID expectedId = UUID.randomUUID();
        given(uuidGenerator.generate()).willReturn(expectedId);

        DeliveryMethod method = converter.toDeliveryMethod(EMAIL_ADDRESS, config);

        assertThat(method.getId()).isEqualTo(expectedId);
    }

    @Test
    void shouldPopulateTypeOnDeliveryMethod() {
        String expectedType = "email";
        given(config.getType()).willReturn(expectedType);

        DeliveryMethod method = converter.toDeliveryMethod(EMAIL_ADDRESS, config);

        assertThat(method.getType()).isEqualTo(expectedType);
    }

    @Test
    void shouldPopulateValueOnDeliveryMethod() {
        DeliveryMethod method = converter.toDeliveryMethod(EMAIL_ADDRESS, config);

        assertThat(method.getValue()).isEqualTo(EMAIL_ADDRESS.getValue());
    }

    @Test
    void shouldPopulateEligibilityOnDeliveryMethod() {
        DeliveryMethod method = converter.toDeliveryMethod(EMAIL_ADDRESS, config);

        assertThat(method.isEligible()).isTrue();
    }

}
