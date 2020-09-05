package uk.co.idv.context.usecases.context.sequence;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.identity.entities.identity.Identity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class SequencesBuilderTest {

    private final SequenceBuilder sequenceBuilder = mock(SequenceBuilder.class);

    private final SequencesBuilder sequencesBuilder = new SequencesBuilder(sequenceBuilder);

    @Test
    void shouldConvertSequencePoliciesToSequences() {
        Identity identity = mock(Identity.class);
        SequencePolicy policy1 = mock(SequencePolicy.class);
        SequencePolicy policy2 = mock(SequencePolicy.class);
        Sequence expectedSequence1 = givenPolicyConvertsToSequence(identity, policy1);
        Sequence expectedSequence2 = givenPolicyConvertsToSequence(identity, policy2);

        Sequences sequences = sequencesBuilder.build(identity, new SequencePolicies(policy1, policy2));

        assertThat(sequences).containsExactly(
                expectedSequence1,
                expectedSequence2
        );
    }

    private Sequence givenPolicyConvertsToSequence(Identity identity, SequencePolicy policy) {
        Sequence sequence = mock(Sequence.class);
        given(sequenceBuilder.build(identity, policy)).willReturn(sequence);
        return sequence;
    }

}
