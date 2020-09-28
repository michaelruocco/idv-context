package uk.co.idv.context.usecases.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;
import uk.co.idv.context.entities.context.sequence.SequencesRequestMother;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequencesBuilderTest {

    private final SequenceBuilder sequenceBuilder = mock(SequenceBuilder.class);

    private final SequencesBuilder sequencesBuilder = new SequencesBuilder(sequenceBuilder);

    @Test
    void shouldConvertSequencePoliciesToSequences() {
        SequencePolicy policy1 = mock(SequencePolicy.class);
        SequencePolicy policy2 = mock(SequencePolicy.class);
        SequencesRequest request = SequencesRequestMother.withPolicies(policy1, policy2);
        Sequence expectedSequence1 = givenPolicyConvertsToSequence(request, policy1);
        Sequence expectedSequence2 = givenPolicyConvertsToSequence(request, policy2);

        Sequences sequences = sequencesBuilder.build(request);

        assertThat(sequences).containsExactly(
                expectedSequence1,
                expectedSequence2
        );
    }

    private Sequence givenPolicyConvertsToSequence(SequencesRequest request, SequencePolicy policy) {
        Sequence sequence = mock(Sequence.class);
        given(sequenceBuilder.build(request, policy)).willReturn(sequence);
        return sequence;
    }

}
