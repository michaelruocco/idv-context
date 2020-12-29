package uk.co.idv.context.adapter.json.context;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VerificationJsonMother {

    static String build() {
        return loadContentFromClasspath("verification/verification.json");
    }

}
