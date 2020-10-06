package uk.co.idv.method.adapter.json.fake;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface FakeMethodJsonMother {

    static String build() {
        return loadContentFromClasspath("fake/fake.json");
    }

}
