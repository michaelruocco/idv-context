package uk.co.idv.context.adapter.json.context.error.contextnotfound;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ContextNotFoundErrorJsonMother {

    static String contextNotFoundErrorJson() {
        return loadContentFromClasspath("context/error/context-not-found-error.json");
    }

}
