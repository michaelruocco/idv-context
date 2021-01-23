package uk.co.idv.context.usecases.context.sequence;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.context.usecases.context.method.MethodsBuilder;

@RequiredArgsConstructor
public class SequenceBuilder {

    private final MethodsBuilder methodsBuilder;

    public Sequence build(SequencesRequest request, SequencePolicy sequencePolicy) {
        return Sequence.builder()
                .name(sequencePolicy.getName())
                .nextMethodsPolicy(sequencePolicy.getNextMethodsPolicy())
                .methods(methodsBuilder.build(request.toMethodsRequest(sequencePolicy)))
                .build();
    }

}
