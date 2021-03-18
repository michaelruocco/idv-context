package uk.co.idv.context.adapter.json.context;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ApiContextJsonMother {

    static String build() {
        return loadContentFromClasspath("context/api/api-context.json");
    }

    static String buildWithProtectedSensitiveData() {
        return loadContentFromClasspath("context/api/api-context-with-protected-sensitive-data.json");
    }

}
