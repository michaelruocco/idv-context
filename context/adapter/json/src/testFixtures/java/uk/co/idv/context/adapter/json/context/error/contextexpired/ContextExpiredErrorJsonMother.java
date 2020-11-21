package uk.co.idv.context.adapter.json.context.error.contextexpired;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ContextExpiredErrorJsonMother {

    static String contextExpiredErrorJson() {
        return loadContentFromClasspath("context/error/context-expired-error.json");
    }

}
