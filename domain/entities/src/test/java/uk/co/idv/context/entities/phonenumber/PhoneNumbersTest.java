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

    @Test
    void shouldReturnIsEmptyFalseIfNotEmpty() {
        assertThat(numbers.isEmpty()).isFalse();
    }

    @Test
    void shouldReturnIsEmptyTrueIfEmpty() {
        PhoneNumbers emptyNumbers = PhoneNumbersMother.empty();

        assertThat(emptyNumbers.isEmpty()).isTrue();
    }

    @Test
    void shouldAddEmailAddressesWithoutDuplicates() {
        PhoneNumber mobileNumber1 = MobilePhoneNumberMother.withNumber("+447089131313");
        PhoneNumbers otherNumbers = new PhoneNumbers(mobileNumber, mobileNumber1);

        PhoneNumbers mergedNumbers = numbers.add(otherNumbers);

        assertThat(mergedNumbers.stream()).containsExactly(
                mobileNumber,
                otherNumber,
                mobileNumber1
        );
    }

}
