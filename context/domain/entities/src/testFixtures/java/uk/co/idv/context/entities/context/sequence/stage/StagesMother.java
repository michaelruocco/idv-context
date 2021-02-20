package uk.co.idv.context.entities.context.sequence.stage;

import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

public interface StagesMother {

    static Stages empty() {
        return new Stages();
    }

    static Stages withOneFakeMethod() {
        return with(FakeMethodMother.build());
    }

    static Stages with(Methods methods) {
        return with(StageMother.with(methods));
    }

    static Stages with(Method... methods) {
        return with(StageMother.with(methods));
    }

    static Stages with(Stage... stages) {
        return new Stages(stages);
    }

}
