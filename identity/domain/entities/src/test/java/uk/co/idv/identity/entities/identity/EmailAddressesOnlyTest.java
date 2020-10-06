package uk.co.idv.identity.entities.identity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailAddressesOnlyTest {

    @Test
    void shouldContainOnlyPhoneNumbers() {
        RequestedData requestedData = new PhoneNumbersOnly();

        assertThat(requestedData).containsExactly(RequestedDataItems.PHONE_NUMBERS);
    }

}
