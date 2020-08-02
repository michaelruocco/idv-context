package uk.co.idv.context.adapter.json.lockout.policy.state;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidLockoutStateCalculatorJsonMother {

    static String invalid() {
        return loadContentFromClasspath("policy/state/invalid-lockout-state-calculator.json");
    }

}
