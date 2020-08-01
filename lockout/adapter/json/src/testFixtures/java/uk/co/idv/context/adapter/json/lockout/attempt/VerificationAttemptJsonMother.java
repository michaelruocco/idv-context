package uk.co.idv.context.adapter.json.lockout.attempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VerificationAttemptJsonMother {

    static String build() {
        return loadContentFromClasspath("attempt/verification-attempt.json");
    }

}
