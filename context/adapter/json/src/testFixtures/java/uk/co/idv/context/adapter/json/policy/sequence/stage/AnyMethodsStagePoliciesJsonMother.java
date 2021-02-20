package uk.co.idv.context.adapter.json.policy.sequence.stage;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface AnyMethodsStagePoliciesJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/sequence/stage/any-methods-stage-policies.json");
    }

}
