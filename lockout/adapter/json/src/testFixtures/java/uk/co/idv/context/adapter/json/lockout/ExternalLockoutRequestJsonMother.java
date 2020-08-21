package uk.co.idv.context.adapter.json.lockout;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ExternalLockoutRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("lockout/external-lockout-request.json");
    }

}
