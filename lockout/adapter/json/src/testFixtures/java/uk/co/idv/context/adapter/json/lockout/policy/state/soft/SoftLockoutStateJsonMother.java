package uk.co.idv.context.adapter.json.lockout.policy.state.soft;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SoftLockoutStateJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/state/soft/soft-lockout-state.json");
    }

}
