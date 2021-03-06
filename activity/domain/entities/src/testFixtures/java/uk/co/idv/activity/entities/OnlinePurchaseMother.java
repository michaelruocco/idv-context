package uk.co.idv.activity.entities;

import org.javamoney.moneta.Money;
import uk.co.idv.activity.entities.onlinepurchase.OnlinePurchase;
import uk.co.idv.activity.entities.onlinepurchase.PurchaseCard;

import javax.money.MonetaryAmount;
import java.time.Instant;

public interface OnlinePurchaseMother {

    static OnlinePurchase withTimestamp(Instant timestamp) {
        return builder().timestamp(timestamp).build();
    }

    static OnlinePurchase withCard(PurchaseCard card) {
        return builder().card(card).build();
    }

    static OnlinePurchase withMerchantName(String merchantName) {
        return builder().merchantName(merchantName).build();
    }

    static OnlinePurchase withReference(String reference) {
        return builder().reference(reference).build();
    }

    static OnlinePurchase withCost(MonetaryAmount cost) {
        return builder().cost(cost).build();
    }

    static OnlinePurchase build() {
        return builder().build();
    }

    static OnlinePurchase.OnlinePurchaseBuilder builder() {
        return OnlinePurchase.builder()
                .timestamp(Instant.parse("2020-06-06T12:36:15.179Z"))
                .card(PurchaseCardMother.build())
                .merchantName("Amazon")
                .reference("ABC123")
                .cost(Money.of(10.99, "GBP"));
    }

}
