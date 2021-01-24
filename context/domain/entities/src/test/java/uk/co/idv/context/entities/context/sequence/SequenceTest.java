package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.stage.MockStagesMother;
import uk.co.idv.context.entities.context.sequence.stage.Stages;
import uk.co.idv.context.entities.context.sequence.stage.StagesMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SequenceTest {

    @Test
    void shouldReturnName() {
        String name = "my-name";

        Sequence sequence = Sequence.builder()
                .name(name)
                .build();

        assertThat(sequence.getName()).isEqualTo(name);
    }

    @Test
    void shouldReturnStages() {
        Stages stages = StagesMother.withOneFakeMethod();

        Sequence sequence = Sequence.builder()
                .stages(stages)
                .build();

        assertThat(sequence.getStages()).isEqualTo(stages);
    }

    @Test
    void shouldReturnEligibleIfAllStagesEligible() {
        Sequence sequence = Sequence.builder()
                .stages(MockStagesMother.allEligible())
                .build();

        boolean eligible = sequence.isEligible();

        assertThat(eligible).isTrue();
    }

    @Test
    void shouldReturnIneligibleIfAtLeastOneStageNotEligible() {
        Sequence sequence = Sequence.builder()
                .stages(MockStagesMother.notAllEligible())
                .build();

        boolean eligible = sequence.isEligible();

        assertThat(eligible).isFalse();
    }

    @Test
    void shouldReturnSuccessfulFromStagesAllSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence = Sequence.builder()
                .stages(MockStagesMother.withAllSuccessful(verifications))
                .build();

        boolean successful = sequence.isSuccessful(verifications);

        assertThat(successful).isTrue();
    }

    @Test
    void shouldReturnCompleteFromStagesAllComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence = Sequence.builder()
                .stages(MockStagesMother.withAllComplete(verifications))
                .build();

        boolean complete = sequence.isComplete(verifications);

        assertThat(complete).isTrue();
    }

    @Test
    void shouldReturnTotalDurationFromStagesAsSequenceDuration() {
        Duration expectedDuration = Duration.ofMinutes(5);
        Sequence sequence = Sequence.builder()
                .stages(MockStagesMother.withTotalDuration(expectedDuration))
                .build();

        Duration totalDuration = sequence.getDuration();

        assertThat(totalDuration).isEqualTo(expectedDuration);
    }

    @Test
    void shouldReturnNextMethodsFromNextMethodsPolicy() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Methods expectedNextMethods = mock(Methods.class);
        Stages stages = MockStagesMother.withNextIncompleteMethods(verifications, expectedNextMethods);
        Sequence sequence = Sequence.builder()
                .stages(stages)
                .build();

        Methods nextMethods = sequence.getNextIncompleteMethods(verifications);

        assertThat(nextMethods).isEqualTo(expectedNextMethods);
    }

    @Test
    void shouldUpdateMethods() {
        UnaryOperator<Method> function = mock(UnaryOperator.class);
        Stages stages = mock(Stages.class);
        Stages updatedStages = MockStagesMother.withUpdatedMethods(function, stages);
        Sequence sequence = Sequence.builder()
                .stages(stages)
                .build();

        Sequence updated = sequence.updateMethods(function);

        assertThat(updated.getName()).isEqualTo(sequence.getName());
        assertThat(updated.getStages()).isEqualTo(updatedStages);
    }

    @Test
    void shouldReturnCompletedMethodCountFromMethods() {
        long expectedCount = 2;
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence = Sequence.builder()
                .stages(MockStagesMother.withCompletedMethodCount(verifications, expectedCount))
                .build();

        long count = sequence.completedMethodCount(verifications);

        assertThat(count).isEqualTo(expectedCount);
    }

}
