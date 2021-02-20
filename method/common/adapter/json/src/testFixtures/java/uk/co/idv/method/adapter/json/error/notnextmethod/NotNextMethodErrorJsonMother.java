package uk.co.idv.method.adapter.json.error.notnextmethod;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface NotNextMethodErrorJsonMother {

    static String notNextMethodErrorJson() {
        return loadContentFromClasspath("error/not-next-method-error.json");
    }

}
