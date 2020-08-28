package uk.co.idv.context.adapter.json.identity;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface IdentityJsonMother {

    static String example() {
        return loadContentFromClasspath("identity/identity.json");
    }

}
