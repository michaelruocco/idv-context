package uk.co.idv.policy.adapter.json;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface PoliciesJsonMother {

    static String build() {
        return loadContentFromClasspath("policies/policies.json");
    }

}
