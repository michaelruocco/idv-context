package uk.co.idv.context.adapter.json.context;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface NextMethodsJsonMother {

    static String build() {
        return loadContentFromClasspath("context/next-methods.json");
    }

}
