package uk.co.idv.lockout.adapter.json.policy.state.nonlocking;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface NonLockingStateJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/state/non-locking/non-locking-state.json");
    }

}
