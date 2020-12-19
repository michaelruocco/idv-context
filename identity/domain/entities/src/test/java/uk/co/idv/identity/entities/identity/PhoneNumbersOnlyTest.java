package uk.co.idv.identity.entities.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesOnly;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumbersOnlyTest {

    @Test
    void shouldContainOnlyEmailAddresses() {
        RequestedData requestedData = new EmailAddressesOnly();

        assertThat(requestedData).containsExactly(RequestedDataItems.EMAIL_ADDRESSES);
    }

}
