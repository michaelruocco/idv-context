package uk.co.idv.context.adapter.json.alias;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface IdvIdJsonMother {

    static String idvId() {
        return loadContentFromClasspath("alias/idv-id.json");
    }

}
