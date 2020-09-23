package uk.co.idv.context.adapter.json.context.create;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface FacadeCreateContextRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("context/create/create-context-request.json");
    }

}
