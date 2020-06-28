package uk.co.idv.context.adapter.json.identity;

import uk.co.mruoc.file.content.ContentLoader;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface IdentityJsonMother {

    static String example() {
        return ContentLoader.loadContentFromClasspath("identity/identity.json");
    }

    static String minimal() {
        return loadContentFromClasspath("identity/minimal-identity.json");
    }

}
