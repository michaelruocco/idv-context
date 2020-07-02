package uk.co.idv.context.adapter.json.error.country;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CountryMismatchErrorJsonMother {

    static String countryMismatchErrorJson() {
        return loadContentFromClasspath("error/country-mismatch-error.json");
    }

}
