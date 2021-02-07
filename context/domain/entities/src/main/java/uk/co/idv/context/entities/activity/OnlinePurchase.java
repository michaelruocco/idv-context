package uk.co.idv.context.entities.activity;

import lombok.Builder;
import lombok.Data;

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
    private final String last4DigitsOfCardNumber;

    @Override
    public String getName() {
        return NAME;
    }

}
