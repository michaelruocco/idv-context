package uk.co.idv.context.entities.alias;

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
    void isCardNumber() {
        Alias cardNumber = DebitCardNumberMother.withValue(VALUE);

        assertThat(cardNumber.isCardNumber()).isTrue();
    }

}
