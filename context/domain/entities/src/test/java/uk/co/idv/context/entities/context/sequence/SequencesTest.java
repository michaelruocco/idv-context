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

}
