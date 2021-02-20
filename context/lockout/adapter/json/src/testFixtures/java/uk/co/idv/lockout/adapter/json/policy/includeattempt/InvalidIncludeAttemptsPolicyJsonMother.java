package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidIncludeAttemptsPolicyJsonMother {

    static String json() {
        return loadContentFromClasspath("policy/include-attempt/invalid-include-attempts-policy.json");
    }

}
