package uk.co.idv.context.adapter.json.context.eligibility;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface EligibilityJsonMother {

    static String eligible() {
        return loadContentFromClasspath("context/eligibility/eligible.json");
    }

    static String ineligible() {
        return loadContentFromClasspath("context/eligibility/ineligible.json");
    }

}
