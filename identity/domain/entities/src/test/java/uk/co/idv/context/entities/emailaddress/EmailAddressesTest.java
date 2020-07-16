package uk.co.idv.context.entities.emailaddress;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailAddressesTest {

    private final String email1 = "joe.bloggs@hotmail.co.uk";
    private final String email2 = "joebloggs@yahoo.co.uk";

    private final EmailAddresses addresses = EmailAddressesMother.with(email1, email2);

    @Test
    void shouldBeIterable() {
        assertThat(addresses).containsExactly(email1, email2);
    }

    @Test
    void shouldReturnStream() {
        assertThat(addresses.stream()).containsExactly(email1, email2);
    }

    @Test
    void shouldReturnIsEmptyFalseIfNotEmpty() {
        assertThat(addresses.isEmpty()).isFalse();
    }

    @Test
    void shouldReturnIsEmptyTrueIfEmpty() {
        EmailAddresses emptyAddresses = EmailAddressesMother.empty();

        assertThat(emptyAddresses.isEmpty()).isTrue();
    }

    @Test
    void shouldAddEmailAddressesWithoutDuplicates() {
        String email3 = "third@email.co.uk";
        EmailAddresses otherAddresses = new EmailAddresses(email2, email3);

        EmailAddresses mergedAddresses = addresses.add(otherAddresses);

        assertThat(mergedAddresses.stream()).containsExactly(email1, email2, email3);
    }

}
