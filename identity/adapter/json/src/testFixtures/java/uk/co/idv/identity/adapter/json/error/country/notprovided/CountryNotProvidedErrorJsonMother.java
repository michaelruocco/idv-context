package uk.co.idv.identity.adapter.json.error.country.notprovided;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CountryNotProvidedErrorJsonMother {

    static String countryNotProvidedErrorJson() {
        return loadContentFromClasspath("error/country-not-provided-error.json");
    }

}
