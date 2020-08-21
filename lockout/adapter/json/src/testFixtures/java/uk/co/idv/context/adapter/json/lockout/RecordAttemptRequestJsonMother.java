package uk.co.idv.context.adapter.json.lockout;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface RecordAttemptRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("lockout/record-attempt-request.json");
    }

}
