package uk.co.idv.context.adapter.json.error.eligibilitynotconfigured;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface EligibilityNotConfiguredErrorJsonMother {

    static String eligibilityNotConfiguredErrorJson() {
        return loadContentFromClasspath("error/eligibility-not-configured-error.json");
    }

}
