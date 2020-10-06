package uk.co.idv.method.usecases.otp.delivery.email;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EmailAddressConverterTest {

    private static final String EMAIL_ADDRESS = "joe.bloggs@hotmail.com";

    private final DeliveryMethodConfig config = mock(DeliveryMethodConfig.class);
    private final IdGenerator idGenerator = mock(IdGenerator.class);

    private final EmailAddressConverter converter = new EmailAddressConverter(idGenerator);

    @Test
    void shouldPopulateIdOnDeliveryMethod() {
        UUID expectedId = UUID.randomUUID();
        given(idGenerator.generate()).willReturn(expectedId);

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

        assertThat(method.getValue()).isEqualTo(EMAIL_ADDRESS);
    }

    @Test
    void shouldPopulateEligibilityOnDeliveryMethod() {
        DeliveryMethod method = converter.toDeliveryMethod(EMAIL_ADDRESS, config);

        assertThat(method.isEligible()).isTrue();
    }

}
