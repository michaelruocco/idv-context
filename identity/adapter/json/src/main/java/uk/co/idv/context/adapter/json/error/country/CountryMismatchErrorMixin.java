package uk.co.idv.context.adapter.json.error.country;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;


public interface CountryMismatchErrorMixin {

    @JsonIgnore
    CountryCode getUpdated();

    @JsonIgnore
    CountryCode getExisting();

}
