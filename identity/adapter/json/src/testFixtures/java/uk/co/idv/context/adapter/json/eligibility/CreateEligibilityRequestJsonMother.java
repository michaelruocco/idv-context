package uk.co.idv.context.adapter.json.eligibility;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CreateEligibilityRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("eligibility/create-eligibility-request.json");
    }

}
