package uk.co.idv.identity.entities.alias;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public abstract class CardNumber implements Alias {

    private final String type;
    private final String value;

    @Override
    public boolean isCardNumber() {
        return true;
    }

}
