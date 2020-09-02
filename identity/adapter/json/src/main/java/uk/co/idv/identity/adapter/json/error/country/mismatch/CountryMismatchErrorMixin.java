package uk.co.idv.identity.adapter.json.error.country.mismatch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;


public interface CountryMismatchErrorMixin {

    @JsonIgnore
    CountryCode getUpdated();

    @JsonIgnore
    CountryCode getExisting();

}
