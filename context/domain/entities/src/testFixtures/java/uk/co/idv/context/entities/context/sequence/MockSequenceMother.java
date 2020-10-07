package uk.co.idv.context.entities.context.sequence;

import uk.co.idv.method.entities.method.Method;

import java.time.Duration;
import java.util.Optional;
import java.util.function.UnaryOperator;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface MockSequenceMother {

    static Sequence mockSequence() {
        return mock(Sequence.class);
    }

    static Sequence givenEligibleSequence() {
        Sequence sequence = mockSequence();
        given(sequence.isEligible()).willReturn(true);
        return sequence;
    }

    static Sequence givenIneligibleSequence() {
        Sequence sequence = mockSequence();
        given(sequence.isEligible()).willReturn(false);
        return sequence;
    }

    static Sequence givenCompleteSequence() {
        Sequence sequence = mockSequence();
        given(sequence.isComplete()).willReturn(true);
        return sequence;
    }

    static Sequence givenIncompleteSequence() {
        Sequence sequence = mockSequence();
        given(sequence.isComplete()).willReturn(false);
        return sequence;
    }

    static Sequence givenSuccessfulSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isSuccessful()).willReturn(true);
        return sequence;
    }

    static Sequence givenUnsuccessfulSequence() {
        Sequence sequence = mockSequence();
        given(sequence.isSuccessful()).willReturn(false);
        return sequence;
    }

    static Sequence givenSequenceWith(Duration duration) {
        Sequence sequence = mockSequence();
        given(sequence.getDuration()).willReturn(duration);
        return sequence;
    }

    static Sequence givenSequenceWithoutNextMethod(String name) {
        Sequence sequence = mockSequence();
        given(sequence.getMethodIfNext(name)).willReturn(Optional.empty());
        return sequence;
    }

    static Sequence givenSequenceWithNextMethod(Method method) {
        Sequence sequence = mockSequence();
        given(sequence.getMethodIfNext(method.getName())).willReturn(Optional.of(method));
        return sequence;
    }

    static Sequence givenUpdatedSequence(UnaryOperator<Method> function, Sequence sequence) {
        Sequence updated = mock(Sequence.class);
        given(sequence.apply(function)).willReturn(updated);
        return updated;
    }

}
