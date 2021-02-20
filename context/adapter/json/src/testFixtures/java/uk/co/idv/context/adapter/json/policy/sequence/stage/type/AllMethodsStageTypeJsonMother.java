package uk.co.idv.context.adapter.json.policy.sequence.stage.type;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface AllMethodsStageTypeJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/sequence/stage/type/all-methods-stage-type.json");
    }

}
