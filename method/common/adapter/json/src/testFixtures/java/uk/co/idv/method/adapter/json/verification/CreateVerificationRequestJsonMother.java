package uk.co.idv.method.adapter.json.verification;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CreateVerificationRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("verification/create-verification-request.json");
    }

}
