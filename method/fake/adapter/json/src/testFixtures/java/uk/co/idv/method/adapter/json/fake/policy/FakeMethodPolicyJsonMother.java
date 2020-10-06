package uk.co.idv.method.adapter.json.fake.policy;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface FakeMethodPolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("fake-policy.json");
    }

}
