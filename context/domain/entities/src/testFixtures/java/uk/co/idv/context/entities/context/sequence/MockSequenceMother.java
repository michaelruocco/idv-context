package uk.co.idv.context.entities.context.sequence;

import uk.co.idv.context.entities.context.method.Method;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;
import uk.co.idv.context.entities.context.method.query.MethodQuery;

import java.time.Duration;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public interface MockSequenceMother {

    static Sequence mockSequence() {
        return mock(Sequence.class);
    }

    static Sequence givenEligibleSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isEligible()).willReturn(true);
        return sequence;
    }

    static Sequence givenIneligibleSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isEligible()).willReturn(false);
        return sequence;
    }

    static Sequence givenCompleteSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isComplete()).willReturn(true);
        return sequence;
    }

    static Sequence givenIncompleteSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isComplete()).willReturn(false);
        return sequence;
    }

    static Sequence givenSuccessfulSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isSuccessful()).willReturn(true);
        return sequence;
    }

    static Sequence givenUnsuccessfulSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isSuccessful()).willReturn(false);
        return sequence;
    }

    static Sequence givenSequenceWith(Duration duration) {
        Sequence sequence = mock(Sequence.class);
        given(sequence.getDuration()).willReturn(duration);
        return sequence;
    }

    static Sequence givenReplacedDeliveryMethodsSequences(Sequence sequence, DeliveryMethods deliveryMethods) {
        Sequence replaced = mock(Sequence.class);
        given(sequence.replaceOtpDeliveryMethods(deliveryMethods)).willReturn(replaced);
        return replaced;
    }

    static <T extends Method> Sequence givenSequenceWithMethodReturnedForQuery(MethodQuery<T> query, T method) {
        Sequence sequence = mock(Sequence.class);
        given(sequence.find(query)).willReturn(Optional.of(method));
        return sequence;
    }

    static <T extends Method> Sequence givenSequenceWithNoResultFor(MethodQuery<T> query) {
        Sequence sequence = mock(Sequence.class);
        given(sequence.find(query)).willReturn(Optional.empty());
        return sequence;
    }

}
