package uk.co.idv.context.entities.phonenumber;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumbersTest {

    private final PhoneNumber number1 = PhoneNumberMother.example();
    private final PhoneNumber number2 = PhoneNumberMother.example1();

    private final PhoneNumbers numbers = PhoneNumbersMother.with(number1, number2);

    @Test
    void shouldBeIterable() {
        assertThat(numbers).containsExactly(number1, number2);
    }

    @Test
    void shouldReturnStream() {
        assertThat(numbers.stream()).containsExactly(number1, number2);
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
    void shouldAddPhoneNumbersWithoutDuplicates() {
        PhoneNumber otherNumber1 = PhoneNumberMother.withNumber("+447089131313");
        PhoneNumbers otherNumbers = new PhoneNumbers(number1, otherNumber1);

        PhoneNumbers mergedNumbers = numbers.add(otherNumbers);

        assertThat(mergedNumbers.stream()).containsExactly(
                number1,
                number2,
                otherNumber1
        );
    }

}
