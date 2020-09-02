package uk.co.idv.identity.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreditCardNumberTest {

    private static final String VALUE = "4929101234567890";

    @Test
    void shouldReturnType() {
        Alias cardNumber = CreditCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.getType()).isEqualTo("credit-card-number");
    }

    @Test
    void shouldReturnValue() {
        Alias cardNumber = CreditCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.getValue()).isEqualTo(VALUE);
    }

    @Test
    void isCardNumber() {
        Alias cardNumber = CreditCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.isCardNumber()).isTrue();
    }

}
