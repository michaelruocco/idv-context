package uk.co.idv.context.adapter.json.policy.method.fake;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface FakeMethodPolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/method/fake/fake-policy.json");
    }

}
