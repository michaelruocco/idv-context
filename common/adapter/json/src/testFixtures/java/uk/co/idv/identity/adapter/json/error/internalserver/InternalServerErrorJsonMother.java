package uk.co.idv.identity.adapter.json.error.internalserver;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InternalServerErrorJsonMother {

    static String internalServerErrorJson() {
        return loadContentFromClasspath("error/internal-server-error.json");
    }

}
