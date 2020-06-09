package uk.co.idv.context.entities.alias;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class DefaultAlias implements Alias {

    private final String type;
    private final String value;

    @Override
    public boolean isCardNumber() {
        return false;
    }

}
