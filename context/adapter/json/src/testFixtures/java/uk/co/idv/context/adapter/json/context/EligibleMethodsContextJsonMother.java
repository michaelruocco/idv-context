package uk.co.idv.context.adapter.json.context;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface EligibleMethodsContextJsonMother {

    static String build() {
        return loadContentFromClasspath("context/eligible-methods-context.json");
    }

}
