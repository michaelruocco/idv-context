package uk.co.idv.context.adapter.json.context.sequence;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SequencesJsonMother {

    static String fakeOnly() {
        return loadContentFromClasspath("context/sequence/sequences.json");
    }

}
