package uk.co.idv.method.entities.otp.delivery.phone;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OtpPhoneNumbersTest {

    @Test
    void shouldBeIterable() {
        OtpPhoneNumber number1 = mock(OtpPhoneNumber.class);
        OtpPhoneNumber number2 = mock(OtpPhoneNumber.class);

        OtpPhoneNumbers numbers = new OtpPhoneNumbers(number1, number2);

        assertThat(numbers).containsExactly(number1, number2);
    }

    @Test
    void shouldReturnStream() {
        OtpPhoneNumber number1 = mock(OtpPhoneNumber.class);
        OtpPhoneNumber number2 = mock(OtpPhoneNumber.class);
        OtpPhoneNumbers numbers = new OtpPhoneNumbers(number1, number2);

        Stream<OtpPhoneNumber> stream = numbers.stream();

        assertThat(stream).containsExactly(number1, number2);
    }

}
