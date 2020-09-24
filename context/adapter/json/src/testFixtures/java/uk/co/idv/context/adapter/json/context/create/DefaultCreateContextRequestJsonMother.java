package uk.co.idv.context.adapter.json.context.create;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DefaultCreateContextRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("context/create/default-create-context-request.json");
    }

}
