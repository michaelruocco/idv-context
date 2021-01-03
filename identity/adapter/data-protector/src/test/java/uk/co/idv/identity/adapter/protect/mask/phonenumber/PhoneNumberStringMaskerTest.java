package uk.co.idv.identity.adapter.protect.mask.phonenumber;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class PhoneNumberStringMaskerTest {

    private final UnaryOperator<String> masker = new PhoneNumberStringMasker();

    @Test
    void shouldMaskPhoneNumberValue() {
        PhoneNumber number = PhoneNumberMother.example();

        String maskedValue = masker.apply(number.getValue());

        assertThat(maskedValue).isEqualTo("**********111");
    }

}
