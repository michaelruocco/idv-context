package uk.co.idv.identity.adapter.json.error.updateidvid;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface CannotUpdateIdvIdErrorJsonMother {

    static String cannotUpdateIdvIdErrorJson() {
        return loadContentFromClasspath("error/cannot-update-idv-id-error.json");
    }

}
