package uk.co.idv.context.adapter.json.verification;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CompleteVerificationRequestJsonMother {

    static String withTimestamp() {
        return loadContentFromClasspath("verification/complete-verification-request-with-timestamp.json");
    }

    static String withoutTimestamp() {
        return loadContentFromClasspath("verification/complete-verification-request-without-timestamp.json");
    }

}
