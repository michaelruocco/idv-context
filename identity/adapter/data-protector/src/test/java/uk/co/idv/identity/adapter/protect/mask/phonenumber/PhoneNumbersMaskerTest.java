package uk.co.idv.identity.adapter.protect.mask.phonenumber;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbersMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumbersMaskerTest {

    private final UnaryOperator<PhoneNumbers> masker = new PhoneNumbersMasker();

    @Test
    void shouldMaskPhoneNumbers() {
        PhoneNumber number1 = PhoneNumberMother.example();
        PhoneNumber number2 = PhoneNumberMother.example1();
        PhoneNumbers numbers = PhoneNumbersMother.with(number1, number2);

        PhoneNumbers maskedNumbers = masker.apply(numbers);

        assertThat(maskedNumbers).containsExactly(
                number1.withValue("**********111"),
                number2.withValue("**********212")
        );
    }

}
