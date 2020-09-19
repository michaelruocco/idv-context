package uk.co.idv.context.adapter.json.policy.sequence;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SequencePolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("sequence/sequence-policy.json");
    }

}
