package uk.co.idv.context.entities.context.sequence.stage;

import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.function.UnaryOperator;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface MockStagesMother {

    static Stages allEligible() {
        Stages stages = mock(Stages.class);
        given(stages.allEligible()).willReturn(true);
        return stages;
    }

    static Stages notAllEligible() {
        Stages stages = mock(Stages.class);
        given(stages.allEligible()).willReturn(false);
        return stages;
    }

    static Stages withAllSuccessful(MethodVerifications verifications) {
        Stages stages = mock(Stages.class);
        given(stages.allSuccessful(verifications)).willReturn(true);
        return stages;
    }

    static Stages withAllComplete(MethodVerifications verifications) {
        Stages stages = mock(Stages.class);
        given(stages.allComplete(verifications)).willReturn(true);
        return stages;
    }

    static Stages withTotalDuration(Duration duration) {
        Stages stages = mock(Stages.class);
        given(stages.getTotalDuration()).willReturn(duration);
        return stages;
    }

    static Stages withNextIncompleteMethods(MethodVerifications verifications, Methods methods) {
        Stages stages = mock(Stages.class);
        given(stages.getNextIncompleteMethods(verifications)).willReturn(methods);
        return stages;
    }

    static Stages withCompletedMethodCount(MethodVerifications verifications, long completedCount) {
        Stages stages = mock(Stages.class);
        given(stages.completedMethodCount(verifications)).willReturn(completedCount);
        return stages;
    }

    static Stages withUpdatedMethods(UnaryOperator<Method> function, Stages stages) {
        Stages updatedStages = mock(Stages.class);
        given(stages.updateMethods(function)).willReturn(updatedStages);
        return updatedStages;
    }

}
