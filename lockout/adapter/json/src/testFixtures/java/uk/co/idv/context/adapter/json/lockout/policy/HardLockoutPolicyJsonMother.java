package uk.co.idv.context.adapter.json.lockout.policy;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface HardLockoutPolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/hard-lockout-policy.json");
    }

}
