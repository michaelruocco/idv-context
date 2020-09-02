package uk.co.idv.identity.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;

@Getter
public class CountryMismatchException extends RuntimeException {

    private final CountryCode existing;
    private final CountryCode updated;

    public CountryMismatchException(CountryCode existing, CountryCode updated) {
        super("cannot merge identities if countries do not match");
        this.existing = existing;
        this.updated = updated;
    }

}
