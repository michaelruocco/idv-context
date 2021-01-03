package uk.co.idv.context.entities.activity;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.CardNumber;
import uk.co.idv.identity.entities.alias.CreditCardNumberMother;

import javax.money.MonetaryAmount;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class OnlinePurchaseTest {

    @Test
    void shouldReturnName() {
        Activity activity = OnlinePurchaseMother.build();

        assertThat(activity.getName()).isEqualTo("online-purchase");
    }

    @Test
    void shouldReturnTimestamp() {
        Instant timestamp = Instant.now();

        Activity activity = OnlinePurchaseMother.withTimestamp(timestamp);

        assertThat(activity.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnCardNumber() {
        CardNumber cardNumber = CreditCardNumberMother.creditCardNumber();

        OnlinePurchase activity = OnlinePurchaseMother.withCardNumber(cardNumber);

        assertThat(activity.getCardNumber()).isEqualTo(cardNumber);
    }

    @Test
    void shouldReturnLast4DigitsOfCardNumber() {
        CardNumber cardNumber = CreditCardNumberMother.creditCardNumber();

        OnlinePurchase activity = OnlinePurchaseMother.withCardNumber(cardNumber);

        assertThat(activity.getLast4DigitsOfCardNumber()).isEqualTo(cardNumber.getLast4Digits());
    }

    @Test
    void shouldReturnCardNumberValue() {
        CardNumber cardNumber = CreditCardNumberMother.creditCardNumber();

        OnlinePurchase activity = OnlinePurchaseMother.withCardNumber(cardNumber);

        assertThat(activity.getCardNumberValue()).isEqualTo(cardNumber.getValue());
    }

    @Test
    void shouldReturnMerchantName() {
        String merchantName = "my-merchant";

        OnlinePurchase activity = OnlinePurchaseMother.withMerchantName(merchantName);

        assertThat(activity.getMerchantName()).isEqualTo(merchantName);
    }

    @Test
    void shouldReturnReference() {
        String reference = "my-reference";

        OnlinePurchase activity = OnlinePurchaseMother.withReference(reference);

        assertThat(activity.getReference()).isEqualTo(reference);
    }

    @Test
    void shouldReturnCost() {
        MonetaryAmount cost = Money.of(99.99, "GBP");

        OnlinePurchase activity = OnlinePurchaseMother.withCost(cost);

        assertThat(activity.getCost()).isEqualTo(cost);
    }

}
