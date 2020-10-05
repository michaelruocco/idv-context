package uk.co.idv.context.adapter.json.context.method.fake;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface FakeMethodJsonMother {

    static String build() {
        return loadContentFromClasspath("context/method/fake/fake.json");
    }

}
