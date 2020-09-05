package uk.co.idv.context.usecases.context.sequence;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SequencesBuilder {

    private final SequenceBuilder sequenceBuilder;

    public Sequences build(Identity identity, SequencePolicies policies) {
        return new Sequences(policies.stream()
                .map(policy -> sequenceBuilder.build(identity, policy))
                .collect(Collectors.toList())
        );
    }

}
