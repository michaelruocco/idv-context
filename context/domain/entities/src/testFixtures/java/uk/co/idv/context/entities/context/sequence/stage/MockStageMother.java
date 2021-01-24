package uk.co.idv.context.entities.context.sequence.stage;

import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.util.Arrays;
import java.util.function.UnaryOperator;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface MockStageMother {

    static Stage mockStage() {
        return mock(Stage.class);
    }

    static Stage givenEligibleStage() {
        Stage stage = mockStage();
        given(stage.isEligible()).willReturn(true);
        return stage;
    }

    static Stage givenIneligibleStage() {
        Stage stage = mockStage();
        given(stage.isEligible()).willReturn(false);
        return stage;
    }

    static Stage givenStageWithIneligibleMethodNames(String... methodNames) {
        Stage stage = mockStage();
        given(stage.getIneligibleMethodNames()).willReturn(Arrays.asList(methodNames));
        return stage;
    }

    static Stage givenCompleteStage(MethodVerifications verifications) {
        Stage stage = mockStage();
        given(stage.isComplete(verifications)).willReturn(true);
        given(stage.getNextMethods(verifications)).willReturn(MethodsMother.empty());
        return stage;
    }

    static Stage givenIncompleteStage(MethodVerifications verifications) {
        Stage stage = mockStage();
        given(stage.isComplete(verifications)).willReturn(false);
        return stage;
    }

    static Stage givenIncompleteStage(MethodVerifications verifications, Method nextMethod) {
        Stage stage = givenIncompleteStage(verifications);
        given(stage.getNextMethods(verifications)).willReturn(MethodsMother.with(nextMethod));
        return stage;
    }

    static Stage givenSuccessfulStage(MethodVerifications verifications) {
        Stage stage = mockStage();
        given(stage.isSuccessful(verifications)).willReturn(true);
        return stage;
    }

    static Stage givenUnsuccessfulStage(MethodVerifications verifications) {
        Stage stage = mockStage();
        given(stage.isSuccessful(verifications)).willReturn(false);
        return stage;
    }

    static Stage givenUpdatedStage(UnaryOperator<Method> function, Stage stage) {
        Stage updatedStage = mockStage();
        given(stage.updateMethods(function)).willReturn(updatedStage);
        return updatedStage;
    }

    static Stage givenStageWithCompletedMethodCount(MethodVerifications verifications, long count) {
        Stage stage = mockStage();
        given(stage.completedMethodCount(verifications)).willReturn(count);
        return stage;
    }

}
