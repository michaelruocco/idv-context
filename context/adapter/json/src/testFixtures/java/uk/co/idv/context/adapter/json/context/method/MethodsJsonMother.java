package uk.co.idv.context.adapter.json.context.method;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface MethodsJsonMother {

    static String fakeOnly() {
        return loadContentFromClasspath("context/method/methods.json");
    }

}
