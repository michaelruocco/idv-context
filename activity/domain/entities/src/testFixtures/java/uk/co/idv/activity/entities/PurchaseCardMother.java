package uk.co.idv.activity.entities;

import uk.co.idv.activity.entities.onlinepurchase.PurchaseCard;

import java.time.YearMonth;

public interface PurchaseCardMother {

    static PurchaseCard build() {
        return builder().build();
    }

    static PurchaseCard withNumber(String number) {
        return builder().number(number).build();
    }

    static PurchaseCard.PurchaseCardBuilder builder() {
        return PurchaseCard.builder()
                .number("4929111111111111")
                .expiry(YearMonth.of(2025, 12));
    }

}
