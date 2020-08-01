package uk.co.idv.context.adapter.json.lockout.attempt;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VerificationAttemptsJsonMother {

    static String build() {
        return loadContentFromClasspath("attempt/verification-attempts.json");
    }

}
