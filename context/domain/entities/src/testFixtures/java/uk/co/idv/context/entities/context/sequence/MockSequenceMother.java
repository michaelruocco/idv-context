package uk.co.idv.context.entities.context.sequence;

import uk.co.idv.context.entities.context.method.MethodsMother;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
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

    static Sequence givenCompleteSequence(MethodVerifications verifications) {
        Sequence sequence = mockSequence();
        given(sequence.isComplete(verifications)).willReturn(true);
        given(sequence.getNextIncompleteMethods(verifications)).willReturn(MethodsMother.empty());
        return sequence;
    }

    static Sequence givenIncompleteSequence(MethodVerifications verifications) {
        Sequence sequence = mockSequence();
        given(sequence.isComplete(verifications)).willReturn(false);
        return sequence;
    }

    static Sequence givenSuccessfulSequence(MethodVerifications verifications) {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isSuccessful(verifications)).willReturn(true);
        return sequence;
    }

    static Sequence givenUnsuccessfulSequence(MethodVerifications verifications) {
        Sequence sequence = mockSequence();
        given(sequence.isSuccessful(verifications)).willReturn(false);
        return sequence;
    }

    static Sequence givenSequenceWith(Duration duration) {
        Sequence sequence = mockSequence();
        given(sequence.getDuration()).willReturn(duration);
        return sequence;
    }

    static Sequence givenSequenceWithNextMethod(MethodVerifications verifications, Method method) {
        Sequence sequence = mockSequence();
        given(sequence.getNextIncompleteMethods(verifications)).willReturn(MethodsMother.with(method));
        return sequence;
    }

    static Sequence givenUpdatedSequence(UnaryOperator<Method> function, Sequence sequence) {
        Sequence updated = mock(Sequence.class);
        given(sequence.updateMethods(function)).willReturn(updated);
        return updated;
    }

    static Sequence givenSequenceWithCompletedCount(MethodVerifications verifications, long count) {
        Sequence sequence = mockSequence();
        given(sequence.completedMethodCount(verifications)).willReturn(count);
        return sequence;
    }

}
