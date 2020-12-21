package uk.co.idv.identity.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DebitCardNumberTest {

    private static final String VALUE = "4929101234567890";

    @Test
    void shouldReturnType() {
        Alias cardNumber = DebitCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.getType()).isEqualTo("debit-card-number");
    }

    @Test
    void shouldReturnValue() {
        Alias cardNumber = DebitCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.getValue()).isEqualTo(VALUE);
    }

    @Test
    void shouldReturnLast4DigitsOfValue() {
        CardNumber cardNumber = DebitCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.getLast4Digits()).isEqualTo("7890");
    }

    @Test
    void shouldReturnLastDigitsIfValueIsShorterThan4Digits() {
        String value = "12";

        CardNumber cardNumber = DebitCardNumberMother.withValue(value);

        assertThat(cardNumber.getLast4Digits()).isEqualTo(value);
    }

    @Test
    void isCardNumber() {
        Alias cardNumber = DebitCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.isCardNumber()).isTrue();
    }

}
