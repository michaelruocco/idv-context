package uk.co.idv.context.usecases.context.sequence;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SequencesBuilder {

    private final SequenceBuilder sequenceBuilder;

    public Sequences build(SequencesRequest request) {
        return new Sequences(request.getPolicies().stream()
                .map(policy -> sequenceBuilder.build(request, policy))
                .collect(Collectors.toList())
        );
    }

}
