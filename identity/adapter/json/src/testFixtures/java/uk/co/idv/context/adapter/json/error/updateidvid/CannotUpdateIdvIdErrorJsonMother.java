package uk.co.idv.context.adapter.json.error.updateidvid;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CannotUpdateIdvIdErrorJsonMother {

    static String cannotUpdateIdvIdError() {
        return loadContentFromClasspath("error/cannot-update-idv-id-error.json");
    }

}
