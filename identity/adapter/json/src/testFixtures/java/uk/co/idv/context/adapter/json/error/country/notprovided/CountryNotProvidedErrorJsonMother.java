package uk.co.idv.context.adapter.json.error.country.notprovided;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CountryNotProvidedErrorJsonMother {

    static String countryNotProvidedErrorJson() {
        return loadContentFromClasspath("error/country-not-provided-error.json");
    }

}
