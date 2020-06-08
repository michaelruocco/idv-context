package uk.co.idv.context.entities.phonenumber;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumbersTest {

    private final PhoneNumber mobileNumber = MobilePhoneNumberMother.mobile();
    private final PhoneNumber otherNumber = OtherPhoneNumberMother.other();

    private final PhoneNumbers numbers = PhoneNumbersMother.with(mobileNumber, otherNumber);

    @Test
    void shouldBeIterable() {
        assertThat(numbers).containsExactly(mobileNumber, otherNumber);
    }

    @Test
    void shouldReturnStream() {
        assertThat(numbers.stream()).containsExactly(mobileNumber, otherNumber);
    }

    @Test
    void shouldReturnMobileNumbers() {
        assertThat(numbers.getMobileNumbers()).containsExactly(mobileNumber);
    }

}
