package uk.co.idv.context.entities.context.sequence;

import uk.co.idv.context.entities.context.sequence.stage.StageMother;
import uk.co.idv.context.entities.context.sequence.stage.StagesMother;

public interface SequenceMother {

    static Sequence fakeOnly() {
        return builder().build();
    }

    static Sequence.SequenceBuilder builder() {
        return Sequence.builder()
                .name("default-sequence")
                .stages(StagesMother.with(StageMother.fakeOnly()));
    }

}
