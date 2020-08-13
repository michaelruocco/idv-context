package uk.co.idv.context.adapter.json.policy.error.policynotfound;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface PolicyNotFoundErrorJsonMother {

    static String policyNotFoundErrorJson() {
        return loadContentFromClasspath("error/policy-not-found-error.json");
    }

}
