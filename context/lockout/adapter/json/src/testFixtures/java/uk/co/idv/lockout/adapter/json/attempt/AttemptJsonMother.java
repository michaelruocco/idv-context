package uk.co.idv.lockout.adapter.json.attempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface AttemptJsonMother {

    static String build() {
        return loadContentFromClasspath("attempt/attempt.json");
    }

}
