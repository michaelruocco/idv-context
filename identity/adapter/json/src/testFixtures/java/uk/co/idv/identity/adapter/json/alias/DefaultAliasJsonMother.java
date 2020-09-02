package uk.co.idv.identity.adapter.json.alias;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DefaultAliasJsonMother {

    static String build() {
        return loadContentFromClasspath("alias/default-alias.json");
    }

}
