package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface IncludeAllAttemptsPolicyJsonMother {

    static String json() {
        return loadContentFromClasspath("policy/include-attempt/include-all-attempts-policy.json");
    }

}
