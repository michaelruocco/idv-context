package uk.co.idv.context.adapter.json.error.country.mismatch;

import com.neovisionaries.i18n.CountryCode;

public interface CountryMismatchErrorMother {

    static CountryMismatchError countryMismatchError() {
        return CountryMismatchError.builder()
                .existing(CountryCode.GB)
                .updated(CountryCode.DE)
                .build();
    }

}
