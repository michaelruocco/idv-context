package uk.co.idv.identity.entities.alias;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.string.StringUtils;

@RequiredArgsConstructor
@Data
public class CardNumber implements Alias {

    public static final String CREDIT_TYPE = "credit-card-number";
    public static final String DEBIT_TYPE = "debit-card-number";

    private final String type;
    private final String value;

    @Override
    public boolean isCardNumber() {
        return true;
    }

    public String getLast4Digits() {
        return StringUtils.extractLastNChars(value, 4);
    }

    public static CardNumber credit(String value) {
        return new CardNumber(CREDIT_TYPE, value);
    }

    public static CardNumber debit(String value) {
        return new CardNumber(DEBIT_TYPE, value);
    }

}
