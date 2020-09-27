package uk.co.idv.context.adapter.json.context.eligibility;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DefaultAsyncEligibilityJsonMother {

    static String eligible() {
        return loadContentFromClasspath("context/eligibility/async-eligible.json");
    }

    static String ineligible() {
        return loadContentFromClasspath("context/eligibility/async-ineligible.json");
    }

}
