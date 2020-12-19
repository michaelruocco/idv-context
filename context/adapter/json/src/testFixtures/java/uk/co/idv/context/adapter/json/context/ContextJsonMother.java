package uk.co.idv.context.adapter.json.context;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ContextJsonMother {

    static String build() {
        return loadContentFromClasspath("context/context.json");
    }

    static String buildWithProtectedSensitiveData() {
        return loadContentFromClasspath("context/context-with-protected-sensitive-data.json");
    }

}
