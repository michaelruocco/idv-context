package uk.co.idv.context.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import lombok.Getter;

@Getter
public class CountryMismatchException extends RuntimeException {

    private final CountryCode existingCountry;
    private final CountryCode countryToAdd;

    public CountryMismatchException(CountryCode existingCountry, CountryCode countryToAdd) {
        super("countries do not match");
        this.existingCountry = existingCountry;
        this.countryToAdd = countryToAdd;
    }

}
