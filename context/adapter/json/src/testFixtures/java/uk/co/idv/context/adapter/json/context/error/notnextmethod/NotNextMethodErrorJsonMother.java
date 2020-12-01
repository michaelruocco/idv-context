package uk.co.idv.context.adapter.json.context.error.notnextmethod;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface NotNextMethodErrorJsonMother {

    static String notNextMethodErrorJson() {
        return loadContentFromClasspath("context/error/not-next-method-error.json");
    }

}
