package uk.co.idv.context.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreditCardNumberTest {

    private static final String VALUE = "4929101234567890";

    @Test
    void shouldReturnType() {
        final Alias cardNumber = CreditCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.getType()).isEqualTo("credit-card-number");
    }

    @Test
    void shouldReturnValue() {
        final Alias cardNumber = CreditCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.getValue()).isEqualTo(VALUE);
    }

    @Test
    void isCardNumber() {
        final Alias cardNumber = CreditCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.isCardNumber()).isTrue();
    }

}
