package uk.co.idv.context.adapter.identity.data.stub.emailaddress;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StubEmailAddressFactoryTest {

    private final StubEmailAddressFactory factory = new StubEmailAddressFactory();

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
