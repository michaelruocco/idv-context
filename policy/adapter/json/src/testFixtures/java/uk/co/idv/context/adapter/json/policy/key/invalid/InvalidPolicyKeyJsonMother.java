package uk.co.idv.context.adapter.json.policy.key.invalid;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidPolicyKeyJsonMother {

    static String invalidKey() {
        return loadContentFromClasspath("policy-key/invalid-policy-key.json");
    }

}
