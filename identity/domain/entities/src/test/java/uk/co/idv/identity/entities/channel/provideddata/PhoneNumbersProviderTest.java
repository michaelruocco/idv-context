package uk.co.idv.identity.entities.channel.provideddata;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.protect.SensitiveDataProtector;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PhoneNumbersProviderTest {

    private final PhoneNumbers phoneNumbers = PhoneNumbersMother.two();
    private final PhoneNumbersProvider provider = new FakePhoneNumbersProvider(phoneNumbers);

    @Test
    void shouldReturnEmailAddressesWithProtectedValues() {
        SensitiveDataProtector protector = mock(SensitiveDataProtector.class);
        PhoneNumbers expectedPhoneNumbers = mock(PhoneNumbers.class);
        given(protector.protect(phoneNumbers)).willReturn(expectedPhoneNumbers);

        PhoneNumbers phoneNumbers = provider.getProtectedPhoneNumbers(protector);

        assertThat(phoneNumbers).isEqualTo(expectedPhoneNumbers);
    }

}
