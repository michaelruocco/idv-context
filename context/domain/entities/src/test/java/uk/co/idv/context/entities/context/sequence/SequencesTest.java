package uk.co.idv.context.entities.context.sequence;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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

}
