package uk.co.idv.context.adapter.identity.data.stub.phonenumber;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.phonenumber.MobilePhoneNumberMother;
import uk.co.idv.context.entities.phonenumber.OtherPhoneNumberMother;

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
        assertThat(factory.getPopulatedData()).containsExactly(
                MobilePhoneNumberMother.withNumber("+447809111111"),
                OtherPhoneNumberMother.withNumber("+441604222222")
        );
    }

}
