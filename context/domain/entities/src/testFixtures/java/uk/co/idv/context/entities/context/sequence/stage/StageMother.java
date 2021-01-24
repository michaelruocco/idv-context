package uk.co.idv.context.entities.context.sequence.stage;


import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.context.entities.policy.sequence.stage.AllMethodsStageTypeMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

public interface StageMother {

    static Stage fakeOnly() {
        return builder().build();
    }

    static Stage with(Method... methods) {
        return with(MethodsMother.with(methods));
    }

    static Stage with(Methods methods) {
        return builder().methods(methods).build();
    }

    static Stage.StageBuilder builder() {
        return Stage.builder()
                .type(AllMethodsStageTypeMother.build())
                .methods(MethodsMother.with(FakeMethodMother.build()));
    }

}
