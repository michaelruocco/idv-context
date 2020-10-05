package uk.co.idv.method.adapter.json.fake.policy;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface FakeMethodConfigJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/method/fake/fake-config.json");
    }

}
