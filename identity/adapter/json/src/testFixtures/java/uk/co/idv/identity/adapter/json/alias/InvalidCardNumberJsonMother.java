package uk.co.idv.identity.adapter.json.alias;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidCardNumberJsonMother {

    static String invalid() {
        return loadContentFromClasspath("alias/invalid-card-number.json");
    }

}
