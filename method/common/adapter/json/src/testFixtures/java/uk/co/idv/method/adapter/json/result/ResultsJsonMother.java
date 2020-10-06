package uk.co.idv.method.adapter.json.result;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ResultsJsonMother {

    static String build() {
        return loadContentFromClasspath("result/results.json");
    }

}
