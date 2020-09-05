package uk.co.idv.context.usecases.context.sequence;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.context.usecases.context.method.MethodsBuilder;
import uk.co.idv.identity.entities.identity.Identity;

@RequiredArgsConstructor
public class SequenceBuilder {

    private final MethodsBuilder methodsBuilder;

    public Sequence build(Identity identity, SequencePolicy sequencePolicy) {
        return Sequence.builder()
                .name(sequencePolicy.getName())
                .methods(methodsBuilder.build(identity, sequencePolicy.getMethodPolicies()))
                .build();
    }

}
