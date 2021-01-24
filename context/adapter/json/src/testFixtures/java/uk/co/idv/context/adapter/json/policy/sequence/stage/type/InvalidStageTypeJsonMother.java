package uk.co.idv.context.adapter.json.policy.sequence.stage.type;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidStageTypeJsonMother {

    static String invalid() {
        return loadContentFromClasspath("policy/sequence/stage/type/invalid-stage-type.json");
    }

}
