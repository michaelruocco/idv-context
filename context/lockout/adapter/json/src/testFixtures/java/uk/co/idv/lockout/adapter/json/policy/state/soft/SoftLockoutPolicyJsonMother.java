package uk.co.idv.lockout.adapter.json.policy.state.soft;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SoftLockoutPolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/state/soft/soft-lockout-policy.json");
    }

}
