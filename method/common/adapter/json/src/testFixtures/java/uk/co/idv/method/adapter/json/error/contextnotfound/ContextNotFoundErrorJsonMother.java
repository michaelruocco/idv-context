package uk.co.idv.method.adapter.json.error.contextnotfound;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ContextNotFoundErrorJsonMother {

    static String contextNotFoundErrorJson() {
        return loadContentFromClasspath("error/context-not-found-error.json");
    }

}
