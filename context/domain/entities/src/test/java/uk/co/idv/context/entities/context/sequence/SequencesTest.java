package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.time.Duration;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenCompleteSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenEligibleSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenIncompleteSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenIneligibleSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenSequenceWith;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenSequenceWithCompletedCount;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenSequenceWithNextMethod;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenSuccessfulSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenUnsuccessfulSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenUpdatedSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.mockSequence;

class SequencesTest {

    @Test
    void shouldBeIterable() {
        Sequence sequence1 = mockSequence();
        Sequence sequence2 = mockSequence();

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences).containsExactly(
                sequence1,
                sequence2
        );
    }

    @Test
    void shouldReturnValues() {
        Sequence sequence1 = mockSequence();
        Sequence sequence2 = mockSequence();

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.getValues()).containsExactly(
                sequence1,
                sequence2
        );
    }

    @Test
    void shouldBeEligibleIfAnySequencesEligible() {
        Sequence sequence1 = givenIneligibleSequence();
        Sequence sequence2 = givenEligibleSequence();

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.isEligible()).isTrue();
    }

    @Test
    void shouldBeIneligibleIfAllSequencesIneligible() {
        Sequence sequence1 = givenIneligibleSequence();
        Sequence sequence2 = givenIneligibleSequence();

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.isEligible()).isFalse();
    }

    @Test
    void shouldBeCompleteIfAnySequencesComplete() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence1 = givenIncompleteSequence(verifications);
        Sequence sequence2 = givenCompleteSequence(verifications);

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.isComplete(verifications)).isTrue();
    }

    @Test
    void shouldBeIncompleteIfAllSequencesHaveNoNextMethod() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence1 = givenIncompleteSequence(verifications);
        Sequence sequence2 = givenIncompleteSequence(verifications);

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.isComplete(verifications)).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfAnySequencesSuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence1 = givenUnsuccessfulSequence(verifications);
        Sequence sequence2 = givenSuccessfulSequence(verifications);

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.isSuccessful(verifications)).isTrue();
    }

    @Test
    void shouldBeUnsuccessfulIfAllSequencesUnsuccessful() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence1 = givenUnsuccessfulSequence(verifications);
        Sequence sequence2 = givenUnsuccessfulSequence(verifications);

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.isSuccessful(verifications)).isFalse();
    }

    @Test
    void shouldReturnZeroDurationIfEmpty() {
        Sequences sequences = new Sequences();

        assertThat(sequences.getDuration()).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldReturnLongestDurationFromSequences() {
        Duration longest = Duration.ofMinutes(5);
        Sequence sequence1 = givenSequenceWith(Duration.ofMinutes(1));
        Sequence sequence2 = givenSequenceWith(longest);

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.getDuration()).isEqualTo(longest);
    }

    @Test
    void shouldUpdateMethodsOnAllSequences() {
        UnaryOperator<Method> function = mock(UnaryOperator.class);
        Sequence sequence1 = mockSequence();
        Sequence sequence2 = mockSequence();
        Sequence updatedSequence1 = givenUpdatedSequence(function, sequence1);
        Sequence updatedSequence2 = givenUpdatedSequence(function, sequence2);
        Sequences sequences = SequencesMother.with(sequence1, sequence2);

        Sequences updated = sequences.updateMethods(function);

        assertThat(updated).containsExactly(updatedSequence1, updatedSequence2);
    }

    @Test
    void shouldReturnCompletedSequencesCount() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence1 = givenCompleteSequence(verifications);
        Sequence sequence2 = givenCompleteSequence(verifications);
        Sequence sequence3 = givenIncompleteSequence(verifications);
        Sequences sequences = SequencesMother.with(sequence1, sequence2, sequence3);

        long count = sequences.completedSequenceCount(verifications);

        assertThat(count).isEqualTo(2);
    }

    @Test
    void shouldReturnCompletedMethodCount() {
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence1 = givenSequenceWithCompletedCount(verifications, 2);
        Sequence sequence2 = givenSequenceWithCompletedCount(verifications,1);
        Sequences sequences = SequencesMother.with(sequence1, sequence2);

        long count = sequences.completedMethodCount(verifications);

        assertThat(count).isEqualTo(3);
    }

    @Test
    void shouldReturnNextMethodsMatchingNameIfPresent() {
        Method method = FakeMethodMother.build();
        MethodVerifications verifications = mock(MethodVerifications.class);
        Sequence sequence1 = givenCompleteSequence(verifications);
        Sequence sequence2 = givenSequenceWithNextMethod(verifications, method);
        Sequences sequences = SequencesMother.with(sequence1, sequence2);

        Methods methods = sequences.getNextMethods(verifications);

        assertThat(methods).containsExactly(method);
    }

}
