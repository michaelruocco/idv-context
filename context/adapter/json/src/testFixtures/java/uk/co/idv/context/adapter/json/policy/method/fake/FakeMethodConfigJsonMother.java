package uk.co.idv.context.adapter.json.policy.method.fake;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface FakeMethodConfigJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/method/fake/fake-config.json");
    }

}
