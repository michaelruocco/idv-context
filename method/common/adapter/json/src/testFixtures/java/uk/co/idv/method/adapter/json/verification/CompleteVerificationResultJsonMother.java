package uk.co.idv.method.adapter.json.verification;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CompleteVerificationResultJsonMother {

    static String successful() {
        return loadContentFromClasspath("verification/complete-verification-result.json");
    }

}
