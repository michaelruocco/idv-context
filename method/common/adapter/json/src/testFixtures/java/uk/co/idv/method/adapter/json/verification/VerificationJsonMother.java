package uk.co.idv.method.adapter.json.verification;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VerificationJsonMother {

    static String incomplete() {
        return loadContentFromClasspath("verification/incomplete-verification.json");
    }

    static String successful() {
        return loadContentFromClasspath("verification/successful-verification.json");
    }

}
