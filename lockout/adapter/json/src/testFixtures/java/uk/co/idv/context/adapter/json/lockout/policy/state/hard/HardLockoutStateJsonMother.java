package uk.co.idv.context.adapter.json.lockout.policy.state.hard;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface HardLockoutStateJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/state/hard/hard-lockout-state.json");
    }

}
