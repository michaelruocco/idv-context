package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.method.otp.Otp;
import uk.co.idv.context.entities.context.method.otp.delivery.DeliveryMethods;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenCompleteSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenEligibleSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenIncompleteSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenIneligibleSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenSequenceWith;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenSequenceWithIncompleteEligibleOtp;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenSuccessfulSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.givenUnsuccessfulSequence;
import static uk.co.idv.context.entities.context.sequence.MockSequenceMother.mockSequence;

class SequencesTest {

    @Test
    void shouldReturnSequences() {
        Sequence sequence1 = mockSequence();
        Sequence sequence2 = mockSequence();

        Sequences sequences = new Sequences(sequence1, sequence2);

        assertThat(sequences).containsExactly(
                sequence1,
                sequence2
        );
    }

    @Test
    void shouldReturnEmptyOptionalIfNoSequencesHaveIncompleteEligibleOtpMethod() {
        Sequence sequence1 = mockSequence();
        Sequence sequence2 = mockSequence();
        Sequences sequences = new Sequences(sequence1, sequence2);

        Optional<Otp> otp = sequences.findNextIncompleteEligibleOtp();

        assertThat(otp).isEmpty();
    }

    @Test
    void shouldReturnOtpIfNoSequenceHasIncompleteEligibleOtpMethod() {
        Sequence sequence1 = mockSequence();
        Otp expectedOtp = mock(Otp.class);
        Sequence sequence2 = givenSequenceWithIncompleteEligibleOtp(expectedOtp);
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
    void shouldReplaceDeliveryMethodsOnAllSequences() {
        Sequence sequence1 = mockSequence();
        Sequence sequence2 = mockSequence();
        DeliveryMethods deliveryMethods = mock(DeliveryMethods.class);
        Sequence replaced1 = givenReplacedDeliveryMethodsSequences(sequence1, deliveryMethods);
        Sequence replaced2 = givenReplacedDeliveryMethodsSequences(sequence2, deliveryMethods);
        Sequences sequences = new Sequences(sequence1, sequence2);

        Sequences replaced = sequences.replaceDeliveryMethods(deliveryMethods);

        assertThat(replaced).containsExactly(replaced1, replaced2);
    }

    private Sequence givenReplacedDeliveryMethodsSequences(Sequence sequence, DeliveryMethods deliveryMethods) {
        Sequence replaced = mock(Sequence.class);
        given(sequence.replaceOtpDeliveryMethods(deliveryMethods)).willReturn(replaced);
        return replaced;
    }

}
