package uk.co.idv.method.adapter.json.error.contextexpired;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ContextExpiredErrorJsonMother {

    static String contextExpiredErrorJson() {
        return loadContentFromClasspath("error/context-expired-error.json");
    }

}
