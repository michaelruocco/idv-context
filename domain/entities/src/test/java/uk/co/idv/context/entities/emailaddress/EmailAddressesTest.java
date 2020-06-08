package uk.co.idv.context.entities.emailaddress;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailAddressesTest {

    private final String email1 = "joe.bloggs@hotmail.co.uk";
    private final String email2 = "joebloggs@yahoo.co.uk";

    private final EmailAddresses numbers = EmailAddressesMother.with(email1, email2);

    @Test
    void shouldBeIterable() {
        assertThat(numbers).containsExactly(email1, email2);
    }

    @Test
    void shouldReturnStream() {
        assertThat(numbers.stream()).containsExactly(email1, email2);
    }

}
