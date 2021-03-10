package uk.co.idv.activity.entities.onlinepurchase;

import lombok.Builder;
import lombok.Data;
import uk.co.mruoc.string.StringUtils;

import java.time.YearMonth;

@Builder
@Data
public class PurchaseCard {

    private final String number;
    private final YearMonth expiry;

    public String getLast4NumberDigits() {
        return StringUtils.extractLastNChars(number, 4);
    }

}
