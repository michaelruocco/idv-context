package uk.co.idv.context.entities.phonenumber;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePhoneNumberTest {

    @Test
    void shouldReturnType() {
        PhoneNumber number = MobilePhoneNumberMother.mobile();

        assertThat(number.getType()).isEqualTo(MobilePhoneNumber.TYPE);
    }

    @Test
    void shouldReturnNumberValue() {
        String value = "+447089123456";

        PhoneNumber number = MobilePhoneNumberMother.withNumber(value);

        assertThat(number.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnIsMobileTrue() {
        PhoneNumber number = MobilePhoneNumberMother.mobile();

        assertThat(number.isMobile()).isTrue();
    }

}
