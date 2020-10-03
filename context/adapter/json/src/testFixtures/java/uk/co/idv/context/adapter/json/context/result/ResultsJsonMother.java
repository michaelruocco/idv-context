package uk.co.idv.context.adapter.json.context.result;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ResultsJsonMother {

    static String build() {
        return loadContentFromClasspath("context/result/results.json");
    }

}
