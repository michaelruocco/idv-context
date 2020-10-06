package uk.co.idv.method.adapter.json.eligibility;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DefaultAsyncEligibilityJsonMother {

    static String eligible() {
        return loadContentFromClasspath("eligibility/async-eligible.json");
    }

    static String ineligible() {
        return loadContentFromClasspath("eligibility/async-ineligible.json");
    }

}
