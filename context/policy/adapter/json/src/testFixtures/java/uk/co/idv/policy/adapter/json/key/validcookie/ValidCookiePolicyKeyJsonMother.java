package uk.co.idv.policy.adapter.json.key.validcookie;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ValidCookiePolicyKeyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy-key/valid-cookie-policy-key.json");
    }

}
