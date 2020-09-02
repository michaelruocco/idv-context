package uk.co.idv.lockout.adapter.json;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ExternalLockoutRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("lockout/external-lockout-request.json");
    }

}
