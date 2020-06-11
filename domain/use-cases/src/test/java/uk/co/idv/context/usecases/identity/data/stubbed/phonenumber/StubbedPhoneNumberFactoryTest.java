package uk.co.idv.context.usecases.identity.data.stubbed.phonenumber;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.phonenumber.MobilePhoneNumber;
import uk.co.idv.context.entities.phonenumber.OtherPhoneNumber;

import static org.assertj.core.api.Assertions.assertThat;

class StubbedPhoneNumberFactoryTest {

    private final StubbedPhoneNumberFactory factory = new StubbedPhoneNumberFactory();

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
        assertThat(factory.getPopulatedData()).containsExactly(
                new MobilePhoneNumber("+447809111111"),
                new OtherPhoneNumber("+441604222222")
        );
    }

}
