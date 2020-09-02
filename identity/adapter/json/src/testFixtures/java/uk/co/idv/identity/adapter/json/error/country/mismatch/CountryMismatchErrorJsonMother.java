package uk.co.idv.identity.adapter.json.error.country.mismatch;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CountryMismatchErrorJsonMother {

    static String countryMismatchErrorJson() {
        return loadContentFromClasspath("error/country-mismatch-error.json");
    }

}
