package uk.co.idv.context.adapter.json.verification;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VerificationJsonMother {

    static String incomplete() {
        return loadContentFromClasspath("verification/incomplete-verification.json");
    }

}
