package uk.co.idv.context.adapter.json.lockout.error.lockedout;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface LockedOutErrorJsonMother {

    static String lockedOutErrorJson() {
        return loadContentFromClasspath("error/locked-out-error.json");
    }

}
