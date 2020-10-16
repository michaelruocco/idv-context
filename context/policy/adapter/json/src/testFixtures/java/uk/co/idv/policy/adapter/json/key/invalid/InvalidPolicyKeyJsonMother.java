package uk.co.idv.policy.adapter.json.key.invalid;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidPolicyKeyJsonMother {

    static String invalidKey() {
        return loadContentFromClasspath("policy-key/invalid-policy-key.json");
    }

}
