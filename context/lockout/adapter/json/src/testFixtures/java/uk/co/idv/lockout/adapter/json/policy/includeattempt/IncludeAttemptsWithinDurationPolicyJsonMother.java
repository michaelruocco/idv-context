package uk.co.idv.lockout.adapter.json.policy.includeattempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface IncludeAttemptsWithinDurationPolicyJsonMother {

    static String json() {
        return loadContentFromClasspath("policy/include-attempt/include-attempts-within-duration-policy.json");
    }

}
