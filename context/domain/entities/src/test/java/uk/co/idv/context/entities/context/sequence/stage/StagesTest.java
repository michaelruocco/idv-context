package uk.co.idv.context.entities.context.sequence.stage;

import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.Collection;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class StagesTest {

    @Test
    void shouldBeIterable() {
        Stage stage1 = MockStageMother.mockStage();
        Stage stage2 = MockStageMother.mockStage();

        Stages stages = new Stages(stage1, stage2);

        assertThat(stages).containsExactly(
                stage1,
                stage2
        );
    }

    @Test
    void shouldReturnValues() {
        Stage stage1 = MockStageMother.mockStage();
        Stage stage2 = MockStageMother.mockStage();

        Stages stages = new Stages(stage1, stage2);

        assertThat(stages.getValues()).containsExactly(
                stage1,
                stage2
        );
    }

    @Test
    void shouldReturnStream() {
        Stage stage1 = MockStageMother.mockStage();
        Stage stage2 = MockStageMother.mockStage();
        Stages stages = new Stages(stage1, stage2);

        Stream<Stage> stream = stages.stream();

        assertThat(stream).containsExactly(
                stage1,
                stage2
        );
    }

    @Test
    void shouldBeEligibleIfAllStagesEligible() {
        Stage eligible1 = MockStageMother.givenEligibleStage();
        Stage eligible2 = MockStageMother.givenEligibleStage();

        Stages stages = new Stages(eligible1, eligible2);

        assertThat(stages.allEligible()).isTrue();
    }

    @Test
    void shouldBeEligibleIfAnyStagesIneligible() {
        Stage eligible = MockStageMother.givenEligibleStage();
        Stage ineligible = MockStageMother.givenIneligibleStage();

        Stages stages = new Stages(eligible, ineligible);

        assertThat(stages.allEligible()).isFalse();
    }

    @Test
    void shouldBeCompleteIfAllStagesComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Stage complete1 = MockStageMother.givenCompleteStage(verifications);
        Stage complete2 = MockStageMother.givenCompleteStage(verifications);

        Stages stages = new Stages(complete1, complete2);

        assertThat(stages.allComplete(verifications)).isTrue();
    }

    @Test
    void shouldBeIncompleteIfAnyStagesIncomplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Stage complete = MockStageMother.givenCompleteStage(verifications);
        Stage incomplete = MockStageMother.givenIncompleteStage(verifications);

        Stages stages = new Stages(complete, incomplete);

        assertThat(stages.allComplete(verifications)).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfAllStagesSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Stage successful1 = MockStageMother.givenSuccessfulStage(verifications);
        Stage successful2 = MockStageMother.givenSuccessfulStage(verifications);

        Stages stages = new Stages(successful1, successful2);

        assertThat(stages.allSuccessful(verifications)).isTrue();
    }

    @Test
    void shouldBeUnsuccessfulIfAnyStagesUnsuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Stage successful = MockStageMother.givenSuccessfulStage(verifications);
        Stage unsuccessful = MockStageMother.givenUnsuccessfulStage(verifications);

        Stages stages = new Stages(successful, unsuccessful);

        assertThat(stages.allSuccessful(verifications)).isFalse();
    }

    @Test
    void shouldReturnZeroTotalDurationIfEmpty() {
        Stages stages = new Stages();

        Duration duration = stages.getTotalDuration();

        assertThat(duration).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldBeEmptyIfContainsNotStages() {
        Stages stages = new Stages();

        boolean empty = stages.isEmpty();

        assertThat(empty).isTrue();
    }

    @Test
    void shouldNotBeEmptyIfContainsStage() {
        Stages stages = new Stages(MockStageMother.mockStage());

        boolean empty = stages.isEmpty();

        assertThat(empty).isFalse();
    }

    @Test
    void shouldUpdateMethodsOnAllStages() {
        UnaryOperator<Method> function = mock(UnaryOperator.class);
        Stage stage1 = MockStageMother.mockStage();
        Stage stage2 = MockStageMother.mockStage();
        Stages stages = StagesMother.with(stage1, stage2);
        Stage updatedStage1 = MockStageMother.givenUpdatedStage(function, stage1);
        Stage updatedStage2 = MockStageMother.givenUpdatedStage(function, stage2);

        Stages updated = stages.updateMethods(function);

        assertThat(updated).containsExactly(updatedStage1, updatedStage2);
    }

    @Test
    void shouldReturnCompletedMethodCountFromEachStage() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Stage stage1 = MockStageMother.givenStageWithCompletedMethodCount(verifications, 1);
        Stage stage2 = MockStageMother.givenStageWithCompletedMethodCount(verifications, 2);
        Stage stage3 = MockStageMother.givenStageWithCompletedMethodCount(verifications, 3);
        Stages stages = StagesMother.with(stage1, stage2, stage3);

        long count = stages.completedMethodCount(verifications);

        assertThat(count).isEqualTo(6);
    }

    @Test
    void shouldReturnNextMethodsEmptyIfAllStagesComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Stage stage1 = MockStageMother.givenCompleteStage(verifications);
        Stage stage2 =  MockStageMother.givenCompleteStage(verifications);
        Stages stages = StagesMother.with(stage1, stage2);

        Methods methods = stages.getNextIncompleteMethods(verifications);

        assertThat(methods).isEmpty();
    }

    @Test
    void shouldReturnNextMethodsFromFirstIncompleteStage() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Stage stage1 = MockStageMother.givenCompleteStage(verifications);
        Method nextMethod = mock(Method.class);
        Stage stage2 =  MockStageMother.givenIncompleteStage(verifications, nextMethod);
        Stages stages = StagesMother.with(stage1, stage2);

        Methods methods = stages.getNextIncompleteMethods(verifications);

        assertThat(methods).containsExactly(nextMethod);
    }

    @Test
    void shouldReturnIneligibleMethodNamesFromStages() {
        String name1 = "method-name1";
        Stage stage1 = MockStageMother.givenStageWithIneligibleMethodNames(name1);
        String name2 = "method-name2";
        Stage stage2 =  MockStageMother.givenStageWithIneligibleMethodNames(name2);
        Stages stages = StagesMother.with(stage1, stage2);

        Collection<String> names = stages.getIneligibleMethodNames();

        assertThat(names).containsExactly(name1, name2);
    }

}
