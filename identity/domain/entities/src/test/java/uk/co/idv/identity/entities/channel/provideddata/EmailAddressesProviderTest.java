package uk.co.idv.identity.entities.channel.provideddata;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;
import uk.co.idv.identity.entities.protect.SensitiveDataProtector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EmailAddressesProviderTest {

    private final EmailAddresses addresses = EmailAddressesMother.two();
    private final EmailAddressesProvider provider = new FakeEmailAddressesProvider(addresses);

    @Test
    void shouldReturnEmailAddressesWithProtectedValues() {
        SensitiveDataProtector protector = mock(SensitiveDataProtector.class);
        EmailAddresses expectedMaskedAddresses = mock(EmailAddresses.class);
        given(protector.protect(addresses)).willReturn(expectedMaskedAddresses);

        EmailAddresses maskedAddresses = provider.getProtectedEmailAddresses(protector);

        assertThat(maskedAddresses).isEqualTo(expectedMaskedAddresses);
    }

}
