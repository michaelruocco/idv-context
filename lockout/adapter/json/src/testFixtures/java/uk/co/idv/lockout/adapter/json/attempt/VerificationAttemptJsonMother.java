package uk.co.idv.lockout.adapter.json.attempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VerificationAttemptJsonMother {

    static String build() {
        return loadContentFromClasspath("attempt/verification-attempt.json");
    }

}
