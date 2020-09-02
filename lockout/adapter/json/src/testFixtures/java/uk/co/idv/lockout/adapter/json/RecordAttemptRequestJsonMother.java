package uk.co.idv.lockout.adapter.json;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface RecordAttemptRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("lockout/record-attempt-request.json");
    }

}
