package uk.co.idv.context.adapter.json.context.sequence;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SequenceJsonMother {

    static String fakeOnly() {
        return loadContentFromClasspath("context/sequence/sequence.json");
    }

}
