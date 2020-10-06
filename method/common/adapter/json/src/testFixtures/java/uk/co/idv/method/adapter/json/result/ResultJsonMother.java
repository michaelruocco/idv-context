package uk.co.idv.method.adapter.json.result;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ResultJsonMother {

    static String build() {
        return loadContentFromClasspath("result/result.json");
    }

}
