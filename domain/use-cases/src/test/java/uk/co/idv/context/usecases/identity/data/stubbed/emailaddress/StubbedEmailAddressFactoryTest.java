package uk.co.idv.context.usecases.identity.data.stubbed.emailaddress;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StubbedEmailAddressFactoryTest {

    private final StubbedEmailAddressFactory factory = new StubbedEmailAddressFactory();

    @Test
    void shouldReturnName() {
        assertThat(factory.getName()).isEqualTo("EmailAddresses");
    }

    @Test
    void shouldReturnEmptyData() {
        assertThat(factory.getEmptyData()).isEmpty();
    }

    @Test
    void shouldReturnPopulatedData() {
        assertThat(factory.getPopulatedData()).containsExactly(
                "joe.bloggs@hotmail.com",
                "joebloggs@live.co.uk"
        );
    }

}
