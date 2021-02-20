package uk.co.idv.identity.entities.alias;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardNumberTest {

    private static final String TYPE = "any-type";
    private static final String VALUE = "4929101234567890";

    @Test
    void shouldReturnType() {
        Alias cardNumber = new CardNumber(TYPE, VALUE);

        assertThat(cardNumber.getType()).isEqualTo(TYPE);
    }

    @Test
    void shouldReturnValue() {
        Alias cardNumber = new CardNumber(TYPE, VALUE);

        assertThat(cardNumber.getValue()).isEqualTo(VALUE);
    }

    @Test
    void shouldReturnLast4DigitsOfValue() {
        CardNumber cardNumber = new CardNumber(TYPE, VALUE);

        assertThat(cardNumber.getLast4Digits()).isEqualTo("7890");
    }

    @Test
    void shouldReturnLastDigitsIfValueIsShorterThan4Digits() {
        String value = "12";

        CardNumber cardNumber = new CardNumber(TYPE, value);

        assertThat(cardNumber.getLast4Digits()).isEqualTo(value);
    }

    @Test
    void isCardNumberTrue() {
        Alias cardNumber = new CardNumber(TYPE, VALUE);

        assertThat(cardNumber.isCardNumber()).isTrue();
    }

    @Test
    void shouldReturnCreditTypeForCreditCard() {
        Alias cardNumber = CardNumber.credit(VALUE);

        assertThat(cardNumber.getType()).isEqualTo("credit-card-number");
    }

    @Test
    void shouldReturnValueForCreditCard() {
        Alias cardNumber = CardNumber.credit(VALUE);

        assertThat(cardNumber.getValue()).isEqualTo(VALUE);
    }

    @Test
    void shouldReturnDebitTypeForDebitCard() {
        Alias cardNumber = CardNumber.debit(VALUE);

        assertThat(cardNumber.getType()).isEqualTo("debit-card-number");
    }

    @Test
    void shouldReturnValueForDebitCard() {
        Alias cardNumber = CardNumber.debit(VALUE);

        assertThat(cardNumber.getValue()).isEqualTo(VALUE);
    }

}
