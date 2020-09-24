package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.Otp;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequencesTest {

    @Test
    void shouldReturnSequences() {
        Sequence sequence1 = mock(Sequence.class);
        Sequence sequence2 = mock(Sequence.class);

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences).containsExactly(
                sequence1,
                sequence2
        );
    }

    @Test
    void shouldReturnEmptyOptionalIfNoSequencesHaveIncompleteEligibleOtpMethod() {
        Sequence sequence1 = mock(Sequence.class);
        Sequence sequence2 = mock(Sequence.class);
        Sequences sequences = new Sequences(sequence1, sequence2);

        Optional<Otp> otp = sequences.findNextIncompleteEligibleOtp();

        assertThat(otp).isEmpty();
    }

    @Test
    void shouldReturnOtpIfNoSequenceHasIncompleteEligibleOtpMethod() {
        Sequence sequence1 = mock(Sequence.class);
        Sequence sequence2 = mock(Sequence.class);
        Otp expectedOtp = mock(Otp.class);
        given(sequence2.findNextIncompleteEligibleOtp()).willReturn(Optional.of(expectedOtp));
        Sequences sequences = new Sequences(sequence1, sequence2);

        Optional<Otp> otp = sequences.findNextIncompleteEligibleOtp();

        assertThat(otp).contains(expectedOtp);
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
        Sequence sequence1 = givenIncompleteSequence();
        Sequence sequence2 = givenCompleteSequence();

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.isComplete()).isTrue();
    }

    @Test
    void shouldBeIncompleteIfAllSequencesIncomplete() {
        Sequence sequence1 = givenIncompleteSequence();
        Sequence sequence2 = givenIncompleteSequence();

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.isComplete()).isFalse();
    }

    @Test
    void shouldBeSuccessfulIfAnySequencesSuccessful() {
        Sequence sequence1 = givenUnsuccessfulSequence();
        Sequence sequence2 = givenSuccessfulSequence();

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.isSuccessful()).isTrue();
    }

    @Test
    void shouldBeUnsuccessfulIfAllSequencesUnsuccessful() {
        Sequence sequence1 = givenUnsuccessfulSequence();
        Sequence sequence2 = givenUnsuccessfulSequence();

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences.isSuccessful()).isFalse();
    }

    private Sequence givenEligibleSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isEligible()).willReturn(true);
        return sequence;
    }

    private Sequence givenIneligibleSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isEligible()).willReturn(false);
        return sequence;
    }

    private Sequence givenCompleteSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isComplete()).willReturn(true);
        return sequence;
    }

    private Sequence givenIncompleteSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isComplete()).willReturn(false);
        return sequence;
    }

    private Sequence givenSuccessfulSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isSuccessful()).willReturn(true);
        return sequence;
    }

    private Sequence givenUnsuccessfulSequence() {
        Sequence sequence = mock(Sequence.class);
        given(sequence.isSuccessful()).willReturn(false);
        return sequence;
    }

}
