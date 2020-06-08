package uk.co.idv.context.entities.phonenumber;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OtherPhoneNumberTest {

    @Test
    void shouldReturnType() {
        PhoneNumber number = OtherPhoneNumberMother.other();

        assertThat(number.getType()).isEqualTo(OtherPhoneNumber.TYPE);
    }

    @Test
    void shouldReturnNumberValue() {
        String value = "+447089123456";

        PhoneNumber number = OtherPhoneNumberMother.withNumber(value);

        assertThat(number.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnIsMobileFalse() {
        final PhoneNumber number = OtherPhoneNumberMother.other();

        assertThat(number.isMobile()).isFalse();
    }

}
