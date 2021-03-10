package uk.co.idv.activity.entities.onlinepurchase;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.activity.entities.Activity;

import javax.money.MonetaryAmount;
import java.time.Instant;

@Builder
@Data
public class OnlinePurchase implements Activity {

    public static final String NAME = "online-purchase";

    private final Instant timestamp;
    private final String merchantName;
    private final String reference;
    private final MonetaryAmount cost;
    private final PurchaseCard card;

    @Override
    public String getName() {
        return NAME;
    }

    public String getLast4CardNumberDigits() {
        return card.getLast4NumberDigits();
    }

}
