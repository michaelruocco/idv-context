package uk.co.idv.context.adapter.identity.service.find.data.phonenumber;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.phonenumber.PhoneNumbersMother;

import static org.assertj.core.api.Assertions.assertThat;

class StubPhoneNumberFactoryTest {

    private final StubPhoneNumberFactory factory = new StubPhoneNumberFactory();

    @Test
    void shouldReturnName() {
        assertThat(factory.getName()).isEqualTo("PhoneNumbers");
    }

    @Test
    void shouldReturnEmptyData() {
        assertThat(factory.getEmptyData()).isEmpty();
    }

    @Test
    void shouldReturnPopulatedData() {
        assertThat(factory.getPopulatedData()).isEqualTo(PhoneNumbersMother.mobileAndOther());
    }

}
