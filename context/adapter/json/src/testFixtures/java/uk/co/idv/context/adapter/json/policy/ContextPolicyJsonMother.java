package uk.co.idv.context.adapter.json.policy;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ContextPolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/context/context-policy.json");
    }

}
