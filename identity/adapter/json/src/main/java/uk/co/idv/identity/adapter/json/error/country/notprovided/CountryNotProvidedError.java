package uk.co.idv.identity.adapter.json.error.country.notprovided;

import lombok.Getter;
import uk.co.idv.identity.adapter.json.error.DefaultApiError;

import java.util.Collections;

@Getter
public class CountryNotProvidedError extends DefaultApiError {

    private static final int STATUS = 400;
    private static final String TITLE = "Cannot create an identity without a country";

    public CountryNotProvidedError() {
        super(STATUS, TITLE, "", Collections.emptyMap());
    }

}
