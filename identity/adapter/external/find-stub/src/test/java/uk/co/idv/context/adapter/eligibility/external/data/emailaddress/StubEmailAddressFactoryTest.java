package uk.co.idv.context.adapter.eligibility.external.data.emailaddress;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;

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
        assertThat(factory.getPopulatedData()).isEqualTo(EmailAddressesMother.two());
    }

}
