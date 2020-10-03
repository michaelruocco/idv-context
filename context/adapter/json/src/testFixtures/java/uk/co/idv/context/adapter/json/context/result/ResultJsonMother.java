package uk.co.idv.context.adapter.json.context.result;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ResultJsonMother {

    static String build() {
        return loadContentFromClasspath("context/result/result.json");
    }

}
