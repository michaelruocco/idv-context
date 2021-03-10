package uk.co.idv.activity.entities;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import uk.co.idv.activity.entities.onlinepurchase.OnlinePurchase;
import uk.co.idv.activity.entities.onlinepurchase.PurchaseCard;

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
    void shouldReturnLast4DigitsOfCardNumber() {
        PurchaseCard card = PurchaseCardMother.build();

        OnlinePurchase activity = OnlinePurchaseMother.withCard(card);

        assertThat(activity.getLast4CardNumberDigits()).isEqualTo(card.getLast4NumberDigits());
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
