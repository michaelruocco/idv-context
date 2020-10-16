package uk.co.idv.lockout.adapter.json.error.lockedout;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface LockedOutErrorJsonMother {

    static String lockedOutErrorJson() {
        return loadContentFromClasspath("error/locked-out-error.json");
    }

}
