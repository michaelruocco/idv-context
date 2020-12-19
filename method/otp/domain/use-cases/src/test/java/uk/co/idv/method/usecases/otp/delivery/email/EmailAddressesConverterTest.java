package uk.co.idv.method.usecases.otp.delivery.email;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethodMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.policy.delivery.email.EmailDeliveryMethodConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EmailAddressesConverterTest {

    private final EmailAddressConverter addressConverter = mock(EmailAddressConverter.class);

    private final EmailAddressesConverter addressesConverter = new EmailAddressesConverter(addressConverter);

    @Test
    void shouldConvertEmailAddressesToDeliveryMethods() {
        EmailDeliveryMethodConfig config = mock(EmailDeliveryMethodConfig.class);
        EmailAddress address1 = emailAddress("bugs.bunny@sky.co.uk");
        EmailAddress address2 = emailAddress("joe.bloggs@hotmail.co.uk");
        DeliveryMethod expectedMethod1 = givenAddressConvertsTo(address1, config);
        DeliveryMethod expectedMethod2 = givenAddressConvertsTo(address2, config);
        EmailAddresses emailAddresses = toEmailAddresses(address1, address2);

        DeliveryMethods methods = addressesConverter.toDeliveryMethods(emailAddresses, config);

        assertThat(methods).containsExactly(expectedMethod1, expectedMethod2);
    }

    private EmailAddress emailAddress(String value) {
        EmailAddress address = mock(EmailAddress.class);
        given(address.getValue()).willReturn(value);
        return address;
    }

    private DeliveryMethod givenAddressConvertsTo(EmailAddress emailAddress, EmailDeliveryMethodConfig config) {
        DeliveryMethod deliveryMethod = DeliveryMethodMother.emailWithValue(emailAddress);
        given(addressConverter.toDeliveryMethod(emailAddress, config)).willReturn(deliveryMethod);
        return deliveryMethod;
    }

    private EmailAddresses toEmailAddresses(EmailAddress... addresses) {
        return EmailAddressesMother.with(addresses);
    }

}
