package uk.co.idv.method.adapter.json.fake;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface FakeMethodJsonMother {

    static String build() {
        return loadContentFromClasspath("fake/fake-method.json");
    }

    static String withResults() {
        return loadContentFromClasspath("fake/fake-method-with-results.json");
    }

}
