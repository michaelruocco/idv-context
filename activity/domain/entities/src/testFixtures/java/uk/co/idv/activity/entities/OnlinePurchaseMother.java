package uk.co.idv.activity.entities;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.time.Instant;

public interface OnlinePurchaseMother {

    static OnlinePurchase withTimestamp(Instant timestamp) {
        return builder().timestamp(timestamp).build();
    }

    static OnlinePurchase withLast4DigitsOfCardNumber(String cardNumber) {
        return builder().last4DigitsOfCardNumber(cardNumber).build();
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
                .last4DigitsOfCardNumber("1111")
                .merchantName("Amazon")
                .reference("ABC123")
                .cost(Money.of(10.99, "GBP"));
    }

}
