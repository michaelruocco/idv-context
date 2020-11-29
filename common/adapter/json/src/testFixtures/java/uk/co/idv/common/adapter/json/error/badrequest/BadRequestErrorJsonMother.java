package uk.co.idv.common.adapter.json.error.badrequest;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface BadRequestErrorJsonMother {

    static String badRequestErrorWithMetaJson() {
        return loadContentFromClasspath("error/bad-request-error-with-meta.json");
    }

}
