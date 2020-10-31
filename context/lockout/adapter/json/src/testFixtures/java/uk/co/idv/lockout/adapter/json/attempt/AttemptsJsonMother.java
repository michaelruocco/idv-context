package uk.co.idv.lockout.adapter.json.attempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface AttemptsJsonMother {

    static String build() {
        return loadContentFromClasspath("attempt/attempts.json");
    }

}
