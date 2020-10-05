package uk.co.idv.context.usecases.context.method.otp.delivery.email;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethods;
import uk.co.idv.method.entities.otp.policy.delivery.DeliveryMethodConfig;
import uk.co.idv.method.entities.otp.policy.delivery.email.EmailDeliveryMethodConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EmailDeliveryMethodConfigConverterTest {

    private final EmailAddressesConverter addressesConverter = mock(EmailAddressesConverter.class);

    private final EmailDeliveryMethodConfigConverter converter = new EmailDeliveryMethodConfigConverter(addressesConverter);

    @Test
    void shouldSupportEmailDeliveryMethodConfig() {
        DeliveryMethodConfig config = mock(EmailDeliveryMethodConfig.class);

        boolean supported = converter.supports(config);

        assertThat(supported).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherDeliveryMethodConfig() {
        DeliveryMethodConfig config = mock(DeliveryMethodConfig.class);

        boolean supported = converter.supports(config);

        assertThat(supported).isFalse();
    }

    @Test
    void shouldConvertConfigToDeliveryMethods() {
        EmailDeliveryMethodConfig config = mock(EmailDeliveryMethodConfig.class);
        Identity identity = IdentityMother.example();
        DeliveryMethods expectedMethods = givenConvertToDeliveryMethods(identity.getEmailAddresses(), config);

        DeliveryMethods methods = converter.toDeliveryMethods(identity, config);

        assertThat(methods).isEqualTo(expectedMethods);
    }

    @Test
    void shouldThrowExceptionIfAttemptToConvertAnyOtherDeliveryMethodConfig() {
        DeliveryMethodConfig config = mock(DeliveryMethodConfig.class);
        Identity identity = mock(Identity.class);

        Throwable error = catchThrowable(() -> converter.toDeliveryMethods(identity, config));

        assertThat(error).isInstanceOf(ClassCastException.class);
    }

    private DeliveryMethods givenConvertToDeliveryMethods(EmailAddresses addresses, EmailDeliveryMethodConfig config) {
        DeliveryMethods methods = mock(DeliveryMethods.class);
        given(addressesConverter.toDeliveryMethods(addresses, config)).willReturn(methods);
        return methods;
    }

}
