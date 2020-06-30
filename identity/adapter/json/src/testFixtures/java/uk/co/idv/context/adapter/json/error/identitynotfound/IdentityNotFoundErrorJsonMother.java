package uk.co.idv.context.adapter.json.error.identitynotfound;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface IdentityNotFoundErrorJsonMother {

    static String identityNotFoundErrorJson() {
        return loadContentFromClasspath("error/identity-not-found-error.json");
    }

}
