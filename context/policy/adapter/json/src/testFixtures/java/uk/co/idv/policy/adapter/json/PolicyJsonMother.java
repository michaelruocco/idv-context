package uk.co.idv.policy.adapter.json;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface PolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("policies/policy.json");
    }

}
