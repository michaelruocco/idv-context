package uk.co.idv.context.usecases.context.method.otp.delivery.email;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethod;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.policy.method.otp.delivery.email.EmailDeliveryMethodConfig;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EmailAddressesConverterTest {

    private final EmailAddressConverter addressConverter = mock(EmailAddressConverter.class);

    private final EmailAddressesConverter addressesConverter = new EmailAddressesConverter(addressConverter);

    @Test
    void shouldConvertEmailAddressesToDeliveryMethods() {
        EmailDeliveryMethodConfig config = mock(EmailDeliveryMethodConfig.class);
        String address1 = "joe.bloggs@hotmail.com";
        String address2 = "micky.mouse@yahoo.com";
        DeliveryMethod expectedMethod1 = givenAddressConvertsTo(address1, config);
        DeliveryMethod expectedMethod2 = givenAddressConvertsTo(address2, config);

        DeliveryMethods methods = addressesConverter.toDeliveryMethods(toEmailAddresses(address1, address2), config);

        assertThat(methods).containsExactly(expectedMethod1, expectedMethod2);
    }

    private DeliveryMethod givenAddressConvertsTo(String emailAddress, EmailDeliveryMethodConfig config) {
        DeliveryMethod deliveryMethod = mock(DeliveryMethod.class);
        given(addressConverter.toDeliveryMethod(emailAddress, config)).willReturn(deliveryMethod);
        return deliveryMethod;
    }

    private EmailAddresses toEmailAddresses(String... addresses) {
        return EmailAddressesMother.with(addresses);
    }

}
