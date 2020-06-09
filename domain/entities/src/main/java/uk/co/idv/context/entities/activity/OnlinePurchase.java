package uk.co.idv.context.entities.activity;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.CardNumber;

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
    private final CardNumber cardNumber;

    @Override
    public String getName() {
        return NAME;
    }

    public String getCardNumberValue() {
        return cardNumber.getValue();
    }

}
