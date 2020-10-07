package uk.co.idv.context.adapter.json.results;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface FacadeRecordResultRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("result/facade-record-result-request.json");
    }

}
