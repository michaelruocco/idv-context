package uk.co.idv.context.entities.alias;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class CardNumber implements Alias {

    private final String type;
    private final String value;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isCardNumber() {
        return true;
    }

}
