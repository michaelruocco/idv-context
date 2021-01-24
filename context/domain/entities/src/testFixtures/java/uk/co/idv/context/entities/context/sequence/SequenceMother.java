package uk.co.idv.context.entities.context.sequence;

import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.context.entities.context.sequence.stage.StageMother;
import uk.co.idv.context.entities.context.sequence.stage.StagesMother;
import uk.co.idv.method.entities.method.Method;

public interface SequenceMother {

    static Sequence fakeOnly() {
        return builder().build();
    }

    static Sequence withMethods(Method... methods) {
        return withMethods(MethodsMother.with(methods));
    }

    static Sequence withMethods(Methods methods) {
        return builder().stages(StagesMother.with(methods)).build();
    }

    static Sequence.SequenceBuilder builder() {
        return Sequence.builder()
                .name("default-sequence")
                .stages(StagesMother.with(StageMother.fakeOnly()));
    }

}
